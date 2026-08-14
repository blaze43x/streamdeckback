package com.streamdeck.streamdeck.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streamdeck.streamdeck.dto.request.LoginRequest;
import com.streamdeck.streamdeck.dto.request.RegistroUsuarioRequest;
import com.streamdeck.streamdeck.dto.response.Respuesta;
import com.streamdeck.streamdeck.facade.AuthFacade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthFacade authFacade;

	@PostMapping("/login")
	public ResponseEntity<Respuesta> login(@Valid @RequestBody LoginRequest request) {
		Respuesta respuesta = authFacade.login(request);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}

	@PostMapping("/registro")
	public ResponseEntity<Respuesta> registrar(@Valid @RequestBody RegistroUsuarioRequest request) {
		Respuesta respuesta = authFacade.registrar(request);
		return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
	}
}
