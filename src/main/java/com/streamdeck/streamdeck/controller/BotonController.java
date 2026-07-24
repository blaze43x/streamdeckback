package com.streamdeck.streamdeck.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streamdeck.streamdeck.dto.Respuesta;
import com.streamdeck.streamdeck.facade.BotonFacade;

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

	@GetMapping("/accion/{idBoton}")
	public ResponseEntity<Respuesta> ejecutarAccion(@PathVariable Integer idBoton) {
		Respuesta respuesta = botonFacade.ejecutarAccion(idBoton);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}
}
