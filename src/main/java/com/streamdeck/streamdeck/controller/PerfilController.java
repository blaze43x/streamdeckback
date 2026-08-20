package com.streamdeck.streamdeck.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streamdeck.streamdeck.dto.request.PerfilRequest;
import com.streamdeck.streamdeck.dto.response.Respuesta;
import com.streamdeck.streamdeck.facade.PerfilFacade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/perfil")
@RequiredArgsConstructor
public class PerfilController {

	private final PerfilFacade perfilFacade;

	@GetMapping
	public ResponseEntity<Respuesta> listar() {
		Respuesta respuesta = perfilFacade.listar();
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Respuesta> actualizar(
			@PathVariable Integer id,
			@Valid @RequestBody PerfilRequest request) {
		Respuesta respuesta = perfilFacade.actualizar(id, request);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Respuesta> eliminar(@PathVariable Integer id) {
		Respuesta respuesta = perfilFacade.eliminar(id);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}
}
