package com.streamdeck.streamdeck.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streamdeck.streamdeck.dto.Respuesta;
import com.streamdeck.streamdeck.facade.PerfilFacade;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/perfil")
@RequiredArgsConstructor
public class PerfilController {

	private final PerfilFacade perfilFacade;

	@GetMapping("/usuario/{idUsuario}")
	public ResponseEntity<Respuesta> listarPorUsuario(@PathVariable Integer idUsuario) {
		Respuesta respuesta = perfilFacade.listarPorUsuario(idUsuario);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}
}
