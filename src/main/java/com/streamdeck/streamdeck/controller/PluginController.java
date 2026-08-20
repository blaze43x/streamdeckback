package com.streamdeck.streamdeck.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.streamdeck.streamdeck.dto.request.PluginRequest;
import com.streamdeck.streamdeck.dto.response.Respuesta;
import com.streamdeck.streamdeck.facade.PluginFacade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/plugin")
@RequiredArgsConstructor
public class PluginController {

	private final PluginFacade pluginFacade;

	@GetMapping
	public ResponseEntity<Respuesta> listar() {
		Respuesta respuesta = pluginFacade.listar();
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@GetMapping("/autor")
	public ResponseEntity<Respuesta> listarPorAutor() {
		Respuesta respuesta = pluginFacade.listarPorAutor();
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@GetMapping("/instalados")
	public ResponseEntity<Respuesta> listarInstalados() {
		Respuesta respuesta = pluginFacade.listarInstalados();
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Respuesta> obtenerPorId(@PathVariable Integer id) {
		Respuesta respuesta = pluginFacade.obtenerPorId(id);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Respuesta> registrar(
			@Valid @RequestPart("plugin") PluginRequest request,
			@RequestPart("archivo") MultipartFile archivo) {
		Respuesta respuesta = pluginFacade.registrar(request, archivo);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Respuesta> eliminar(@PathVariable Integer id) {
		Respuesta respuesta = pluginFacade.eliminar(id);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@DeleteMapping("/{id}/desinstalar")
	public ResponseEntity<Respuesta> desinstalar(@PathVariable Integer id) {
		Respuesta respuesta = pluginFacade.desinstalar(id);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@PostMapping("/{id}/descargar")
	public ResponseEntity<?> descargar(@PathVariable Integer id) {
		return pluginFacade.descargar(id);
	}
}
