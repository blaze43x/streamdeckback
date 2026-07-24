package com.streamdeck.streamdeck.util.obs;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Component;

import com.streamdeck.streamdeck.dto.Respuesta;

import lombok.RequiredArgsConstructor;

/**
 * Acciones ejecutables desde los botones del Stream Deck.
 */
@Component
@RequiredArgsConstructor
public class ObsAcciones {

	private final ObsService obsService;

	public Respuesta cambiarEscena(String nombreEscena) {
		try {
			/*List<String> escenas = obsService.listarEscenas();
			if (!escenas.contains(nombreEscena)) {
				Respuesta respuesta = Respuesta.internalBadRequest();
				respuesta.setMessage("La escena " + nombreEscena + " no existe");
				return respuesta;
			}*/

			obsService.cambiarEscena(nombreEscena);
			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Escena cambiada correctamente");
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo cambiar la escena: " + e.getMessage());
			return respuesta;
		}
	}

	public Respuesta abrirAplicacion(String nombreAplicacion) {
		try {
			Process proceso = new ProcessBuilder("cmd", "/c", "start", "", nombreAplicacion).start();
			proceso.waitFor();
			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Aplicacion abierta correctamente");
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo abrir la aplicación: " + e.getMessage());
			return respuesta;
		}
	}

	public Respuesta reproducirFuente(String nombreFuente) {
		try {
			obsService.reproducirFuente(nombreFuente);
			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Fuente reproducida correctamente");
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo reproducir la fuente: " + e.getMessage());
			return respuesta;
		}
	}

	/**
	 * Ejecuta en CMD el texto recibido (una o varias líneas).
	 * Ejemplo:
	 * <pre>
	 * E:
	 * cd E:\Development\jboss-4.2.3.GA\bin
	 * run.bat -b 0.0.0.0
	 * </pre>
	 */
	public Respuesta ejecutarComando(String script) {
		if (script == null || script.isBlank()) {
			Respuesta respuesta = Respuesta.internalBadRequest();
			respuesta.setMessage("El comando CMD está vacío");
			return respuesta;
		}

		try {
			Path bat = Files.createTempFile("streamdeck-cmd-", ".bat");
			Files.writeString(bat, script, StandardCharsets.UTF_8);

			// start abre otra ventana y no bloquea el hilo del servidor
			new ProcessBuilder(
					"cmd", "/c", "start", "StreamDeck CMD", "cmd", "/c",
					bat.toAbsolutePath().toString())
					.start();

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Comando CMD ejecutado");
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo ejecutar el comando CMD: " + e.getMessage());
			return respuesta;
		}
	}
}
