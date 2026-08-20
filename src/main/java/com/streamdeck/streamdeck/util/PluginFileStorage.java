package com.streamdeck.streamdeck.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PluginFileStorage {

	private final Path storagePath;

	public PluginFileStorage(@Value("${plugins.storage-path}") String storagePath) {
		this.storagePath = Paths.get(storagePath).toAbsolutePath().normalize();
	}

	public String guardar(MultipartFile archivo) throws IOException {
		if (archivo == null || archivo.isEmpty()) {
			throw new IllegalArgumentException("El archivo del plugin es obligatorio");
		}

		String nombreOriginal = archivo.getOriginalFilename();
		if (nombreOriginal == null || !nombreOriginal.toLowerCase().endsWith(".py")) {
			throw new IllegalArgumentException("Solo se permiten archivos .py");
		}

		Files.createDirectories(storagePath);

		String nombreSeguro = UUID.randomUUID() + ".py";
		Path destino = storagePath.resolve(nombreSeguro);
		archivo.transferTo(destino);

		return "plugins/" + nombreSeguro;
	}

	public Path obtenerRutaAbsoluta(String rutaPlugin) {
		if (rutaPlugin == null || rutaPlugin.isBlank()) {
			throw new IllegalArgumentException("La ruta del plugin es inválida");
		}

		String nombreArchivo = rutaPlugin.replace("\\", "/");
		int ultimoSlash = nombreArchivo.lastIndexOf('/');
		if (ultimoSlash >= 0) {
			nombreArchivo = nombreArchivo.substring(ultimoSlash + 1);
		}

		if (nombreArchivo.isBlank()) {
			throw new IllegalArgumentException("La ruta del plugin es inválida");
		}

		return storagePath.resolve(nombreArchivo).normalize();
	}
}
