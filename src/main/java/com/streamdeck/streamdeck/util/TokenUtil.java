package com.streamdeck.streamdeck.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.streamdeck.streamdeck.model.Usuario;
import com.streamdeck.streamdeck.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TokenUtil {

	private final UsuarioService usuarioService;

	public Usuario obtenerUsuarioDelToken() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getPrincipal() == null) {
			return null;
		}
		String correo = authentication.getPrincipal().toString();
		return usuarioService.getByCorreo(correo);
	}
}
