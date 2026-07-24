package com.streamdeck.streamdeck.util.obs;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class ObsConexion {

	private static final Logger logger = Logger.getLogger(ObsConexion.class.getName());

	private final ObjectMapper mapper = new ObjectMapper();

	private volatile boolean autenticado = false;
	private WebSocketClient client;

	@Value("${obs.host}")
	private String host;

	@Value("${obs.port}")
	private int port;

	@Value("${obs.password}")
	private String password;

	@PostConstruct
	public void init() {
		conectar();
	}

	@PreDestroy
	public void desconectar() {
		if (client != null && client.isOpen()) {
			client.close();
		}
	}

	/**
	 * Si no hay sesión válida con OBS, intenta reconectar.
	 */
	public synchronized void asegurarConexion() {
		if (autenticado && client != null && client.isOpen()) {
			return;
		}
		conectar();
		if (!autenticado || client == null || !client.isOpen()) {
			throw new IllegalStateException("OBS no está autenticado");
		}
	}

	public JsonNode enviar(String json) {
		asegurarConexion();
		client.send(json);
		return mapper.readTree(json);
	}

	public boolean isAutenticado() {
		return autenticado && client != null && client.isOpen();
	}

	private void conectar() {
		autenticado = false;
		if (client != null) {
			try {
				client.close();
			} catch (Exception ignored) {
				// ignore
			}
		}

		CountDownLatch listo = new CountDownLatch(1);
		URI uri = URI.create("ws://" + host + ":" + port);
		client = new WebSocketClient(uri) {
			@Override
			public void onOpen(ServerHandshake handshakedata) {
				logger.info("WebSocket conectado a OBS");
			}

			@Override
			public void onMessage(String message) {
				procesarMensaje(message, listo);
			}

			@Override
			public void onClose(int code, String reason, boolean remote) {
				autenticado = false;
				listo.countDown();
				logger.info("WebSocket desconectado de OBS (code=" + code + ", reason=" + reason + ")");
			}

			@Override
			public void onError(Exception ex) {
				autenticado = false;
				listo.countDown();
				logger.severe("Error en WebSocket OBS: " + ex.getMessage());
			}
		};

		try {
			client.connect();
			listo.await(5, TimeUnit.SECONDS);
		} catch (Exception e) {
			autenticado = false;
			logger.severe("No se pudo conectar a OBS: " + e.getMessage());
		}
	}

	private void procesarMensaje(String message, CountDownLatch listo) {
		try {
			JsonNode root = mapper.readTree(message);
			int op = root.path("op").asInt();
			JsonNode d = root.path("d");

			if (op == 0) {
				responderIdentify(d);
			} else if (op == 2) {
				autenticado = true;
				listo.countDown();
				logger.info("Autenticado con OBS");
			}
		} catch (Exception e) {
			logger.severe("Error al procesar mensaje de OBS: " + e.getMessage());
			listo.countDown();
		}
	}

	private void responderIdentify(JsonNode d) throws Exception {
		String salt = d.path("authentication").path("salt").asString();
		String challenge = d.path("authentication").path("challenge").asString();
		String auth = generarAuth(salt, challenge);

		String identify = """
			{
			  "op": 1,
			  "d": {
			    "rpcVersion": 1,
			    "authentication": "%s"
			  }
			}
			""".formatted(auth);

		client.send(identify);
		logger.info("Identificacion enviada a OBS");
	}

	private String generarAuth(String salt, String challenge) throws Exception {
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		String secret = Base64.getEncoder().encodeToString(sha.digest((password + salt).getBytes(StandardCharsets.UTF_8)));
		sha.reset();
		return Base64.getEncoder().encodeToString(sha.digest((secret + challenge).getBytes(StandardCharsets.UTF_8)));
	}
}
