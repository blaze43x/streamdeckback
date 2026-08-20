package com.streamdeck.streamdeck.facade.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.streamdeck.streamdeck.dto.request.PerfilRequest;
import com.streamdeck.streamdeck.dto.response.PerfilResponse;
import com.streamdeck.streamdeck.dto.response.Respuesta;
import com.streamdeck.streamdeck.facade.PerfilFacade;
import com.streamdeck.streamdeck.model.Boton;
import com.streamdeck.streamdeck.model.Perfil;
import com.streamdeck.streamdeck.model.Usuario;
import com.streamdeck.streamdeck.service.BotonService;
import com.streamdeck.streamdeck.service.PerfilService;
import com.streamdeck.streamdeck.util.TokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerfilFacadeImpl implements PerfilFacade {

	private final PerfilService perfilService;
	private final BotonService botonService;
	private final TokenUtil tokenUtil;

	@Override
	public Respuesta listar() {
		try {
			Usuario usuario = tokenUtil.obtenerUsuarioDelToken();
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			List<PerfilResponse> perfiles = perfilService.listarPorUsuario(usuario.getId()).stream()
					.map(PerfilResponse::new)
					.toList();

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

	@Override
	public Respuesta actualizar(Integer id, PerfilRequest request) {
		try {
			Usuario usuario = tokenUtil.obtenerUsuarioDelToken();
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			Perfil perfil = perfilService.get(id);
			if (perfil == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El perfil " + id + " no existe");
				return respuesta;
			}

			if (perfil.getUsuario() == null || !usuario.getId().equals(perfil.getUsuario().getId())) {
				Respuesta respuesta = Respuesta.internalForbidden();
				respuesta.setMessage("No tienes permiso para actualizar este perfil");
				return respuesta;
			}

			perfil.setCnombre(request.getCnombre().trim());
			Perfil actualizado = perfilService.save(perfil);

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Perfil actualizado correctamente");
			respuesta.setData(new PerfilResponse(actualizado));
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo actualizar el perfil: " + e.getMessage());
			return respuesta;
		}
	}

	@Override
	public Respuesta eliminar(Integer id) {
		try {
			Usuario usuario = tokenUtil.obtenerUsuarioDelToken();
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			Perfil perfil = perfilService.get(id);
			if (perfil == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El perfil " + id + " no existe");
				return respuesta;
			}

			if (perfil.getUsuario() == null || !usuario.getId().equals(perfil.getUsuario().getId())) {
				Respuesta respuesta = Respuesta.internalForbidden();
				respuesta.setMessage("No tienes permiso para eliminar este perfil");
				return respuesta;
			}

			List<Boton> botones = botonService.listarPorPerfil(perfil.getId());
			for (Boton boton : botones) {
				botonService.delete(boton.getId());
			}

			perfilService.delete(perfil.getId());

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Perfil eliminado correctamente");
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo eliminar el perfil: " + e.getMessage());
			return respuesta;
		}
	}
}
