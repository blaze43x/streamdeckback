package com.streamdeck.streamdeck.facade.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.streamdeck.streamdeck.dto.Respuesta;
import com.streamdeck.streamdeck.dto.response.ResponsePerfil;
import com.streamdeck.streamdeck.facade.PerfilFacade;
import com.streamdeck.streamdeck.service.PerfilService;
import com.streamdeck.streamdeck.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerfilFacadeImpl implements PerfilFacade {

	private final PerfilService perfilService;
	private final UsuarioService usuarioService;

	@Override
	public Respuesta listarPorUsuario(Integer idUsuario) {
		try {
			if (usuarioService.get(idUsuario) == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El usuario " + idUsuario + " no existe");
				return respuesta;
			}

			List<ResponsePerfil> perfiles = perfilService.listarPorUsuario(idUsuario).stream()
					.map(perfil -> new ResponsePerfil(perfil.getId(), perfil.getCnombre()))
					.collect(Collectors.toList());

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Perfiles obtenidos correctamente");
			respuesta.setData(perfiles);
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudieron listar los perfiles: " + e.getMessage());
			return respuesta;
		}
	}
}
