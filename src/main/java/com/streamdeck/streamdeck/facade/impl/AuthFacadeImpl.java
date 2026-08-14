package com.streamdeck.streamdeck.facade.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.streamdeck.streamdeck.dto.request.LoginRequest;
import com.streamdeck.streamdeck.dto.request.RegistroUsuarioRequest;
import com.streamdeck.streamdeck.dto.request.UsuarioRequest;
import com.streamdeck.streamdeck.dto.response.Respuesta;
import com.streamdeck.streamdeck.dto.response.UsuarioResponse;
import com.streamdeck.streamdeck.facade.AuthFacade;
import com.streamdeck.streamdeck.model.Usuario;
import com.streamdeck.streamdeck.security.JwtService;
import com.streamdeck.streamdeck.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade {

	private final UsuarioService usuarioService;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	@Override
	public Respuesta login(LoginRequest request) {
		try {
			if (request.getCorreo() == null || request.getCorreo().isBlank()
					|| request.getPassword() == null || request.getPassword().isBlank()) {
				Respuesta respuesta = Respuesta.internalBadRequest();
				respuesta.setMessage("Correo y contraseña son obligatorios");
				return respuesta;
			}

			Usuario usuario = usuarioService.getByCorreo(request.getCorreo().trim());
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("Credenciales inválidas");
				return respuesta;
			}

			if (!Boolean.TRUE.equals(usuario.getBactivo())) {
				Respuesta respuesta = Respuesta.internalForbidden();
				respuesta.setMessage("Esta cuenta está inhabilitada");
				return respuesta;
			}

			if (!passwordEncoder.matches(request.getPassword(), usuario.getCpassword())) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("Credenciales inválidas");
				return respuesta;
			}

			String token = jwtService.generateToken(usuario);
			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Login exitoso");
			respuesta.setData(token);
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo iniciar sesión: " + e.getMessage());
			return respuesta;
		}
	}

	@Override
	public Respuesta registrar(RegistroUsuarioRequest request) {
		try {
			UsuarioRequest datos = request.getUsuario();
			String correo = datos.getCcorreo().trim();
			if (usuarioService.getByCorreo(correo) != null) {
				Respuesta respuesta = Respuesta.internalConflict();
				respuesta.setMessage("Ya existe un usuario con ese correo");
				return respuesta;
			}

			Usuario usuario = new Usuario();
			usuario.setCnombre(datos.getCnombre().trim());
			usuario.setCcorreo(correo);
			usuario.setCpassword(passwordEncoder.encode(request.getPassword()));

			Usuario usuarioGuardado = usuarioService.save(usuario);

			UsuarioResponse usuarioResponse = new UsuarioResponse(usuarioGuardado);

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Usuario registrado correctamente");
			respuesta.setData(usuarioResponse);
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo registrar el usuario: " + e.getMessage());
			return respuesta;
		}
	}
}
