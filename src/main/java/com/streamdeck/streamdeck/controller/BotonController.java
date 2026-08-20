package com.streamdeck.streamdeck.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streamdeck.streamdeck.dto.request.BotonRequest;
import com.streamdeck.streamdeck.dto.response.Respuesta;
import com.streamdeck.streamdeck.facade.BotonFacade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/boton")
@RequiredArgsConstructor
public class BotonController {

	private final BotonFacade botonFacade;

	@GetMapping("/perfil/{idPerfil}")
	public ResponseEntity<Respuesta> listarPorPerfil(@PathVariable Integer idPerfil) {
		Respuesta respuesta = botonFacade.listarPorPerfil(idPerfil);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Respuesta> obtenerPorId(@PathVariable Integer id) {
		Respuesta respuesta = botonFacade.obtenerPorId(id);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@PostMapping
	public ResponseEntity<Respuesta> registrar(@Valid @RequestBody BotonRequest request) {
		Respuesta respuesta = botonFacade.registrar(request);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Respuesta> actualizar(
			@PathVariable Integer id,
			@Valid @RequestBody BotonRequest request) {
		Respuesta respuesta = botonFacade.actualizar(id, request);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Respuesta> eliminar(@PathVariable Integer id) {
		Respuesta respuesta = botonFacade.eliminar(id);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}
}
