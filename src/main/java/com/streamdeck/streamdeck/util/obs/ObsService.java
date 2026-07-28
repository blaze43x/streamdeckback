package com.streamdeck.streamdeck.util.obs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.JsonNode;

/**
 * Acciones de negocio sobre OBS (usa {@link ObsConexion} para enviar comandos).
 */
@Component
@RequiredArgsConstructor
public class ObsService {

	private final ObsConexion obsConexion;

	public void cambiarEscena(String nombreEscena) {
		String requestId = UUID.randomUUID().toString();
		String json = """
			{
			  "op": 6,
			  "d": {
			    "requestType": "SetCurrentProgramScene",
			    "requestId": "%s",
			    "requestData": {
			      "sceneName": "%s"
			    }
			  }
			}
			""".formatted(requestId, nombreEscena);

		obsConexion.enviar(json);
	}

	public List<String> listarEscenas() {
		String requestId = UUID.randomUUID().toString();
		String json = """
			{
			  "op": 6,
			  "d": {
				"requestType": "GetSceneList",
				"requestId": "%s",
				"requestData": {}
			  }
			}
			""".formatted(requestId);

		JsonNode response = obsConexion.enviar(json);

		List<String> nombres = new ArrayList<>();
		for (JsonNode scene : response.path("responseData").path("scenes")) {
			nombres.add(scene.path("sceneName").asString());
		}
		return nombres;
	}

	public void reproducirVariasFuentes(String valor) {
		if (valor == null || valor.isBlank()) {
			return;
		}
		for (String linea : valor.split("[\\n;]+")) {
			String nombre = linea.trim();
			if (!nombre.isEmpty()) {
				reproducirFuente(nombre);
			}
		}
	}

	private void reproducirFuente(String nombreFuente) {
		String requestId = UUID.randomUUID().toString();
		String json = """
			{
			"op": 6,
			"d": {
				"requestType": "TriggerMediaInputAction",
				"requestId": "%s",
				"requestData": {
				"inputName": "%s",
				"mediaAction": "OBS_WEBSOCKET_MEDIA_INPUT_ACTION_RESTART"
				}
			}
			}
			""".formatted(requestId, nombreFuente);
		obsConexion.enviar(json);
	}
}
