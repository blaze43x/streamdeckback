package com.streamdeck.streamdeck.facade.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.streamdeck.streamdeck.dto.request.BotonRequest;
import com.streamdeck.streamdeck.dto.response.BotonResponse;
import com.streamdeck.streamdeck.dto.response.Respuesta;
import com.streamdeck.streamdeck.facade.BotonFacade;
import com.streamdeck.streamdeck.model.Boton;
import com.streamdeck.streamdeck.model.Perfil;
import com.streamdeck.streamdeck.model.Usuario;
import com.streamdeck.streamdeck.model.UsuarioPlugin;
import com.streamdeck.streamdeck.service.BotonService;
import com.streamdeck.streamdeck.service.PerfilService;
import com.streamdeck.streamdeck.service.UsuarioPluginService;
import com.streamdeck.streamdeck.util.TokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BotonFacadeImpl implements BotonFacade {

	private final BotonService botonService;
	private final PerfilService perfilService;
	private final UsuarioPluginService usuarioPluginService;
	private final TokenUtil tokenUtil;

	@Override
	public Respuesta listarPorPerfil(Integer idPerfil) {
		try {
			Usuario usuario = tokenUtil.obtenerUsuarioDelToken();
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			Perfil perfil = perfilService.get(idPerfil);
			if (perfil == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El perfil " + idPerfil + " no existe");
				return respuesta;
			}

			if (perfil.getUsuario() == null || !usuario.getId().equals(perfil.getUsuario().getId())) {
				Respuesta respuesta = Respuesta.internalForbidden();
				respuesta.setMessage("No tienes permiso para ver los botones de este perfil");
				return respuesta;
			}

			List<BotonResponse> botones = botonService.listarPorPerfil(idPerfil).stream()
					.map(BotonResponse::new)
					.toList();

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Botones obtenidos correctamente");
			respuesta.setData(botones);
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudieron listar los botones: " + e.getMessage());
			return respuesta;
		}
	}

	@Override
	public Respuesta obtenerPorId(Integer id) {
		try {
			Usuario usuario = tokenUtil.obtenerUsuarioDelToken();
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			Boton boton = botonService.get(id);
			if (boton == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El botón " + id + " no existe");
				return respuesta;
			}

			Perfil perfil = boton.getPerfil();
			if (perfil == null || perfil.getUsuario() == null
					|| !usuario.getId().equals(perfil.getUsuario().getId())) {
				Respuesta respuesta = Respuesta.internalForbidden();
				respuesta.setMessage("No tienes permiso para ver este botón");
				return respuesta;
			}

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Botón obtenido correctamente");
			respuesta.setData(new BotonResponse(boton));
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo obtener el botón: " + e.getMessage());
			return respuesta;
		}
	}

	@Override
	public Respuesta registrar(BotonRequest request) {
		try {
			Usuario usuario = tokenUtil.obtenerUsuarioDelToken();
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			Perfil perfil = perfilService.get(request.getIdperfil());
			if (perfil == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El perfil " + request.getIdperfil() + " no existe");
				return respuesta;
			}

			if (perfil.getUsuario() == null || !usuario.getId().equals(perfil.getUsuario().getId())) {
				Respuesta respuesta = Respuesta.internalForbidden();
				respuesta.setMessage("No tienes permiso para registrar botones en este perfil");
				return respuesta;
			}

			UsuarioPlugin usuarioPlugin = null;
			if (request.getIdusuarioplugin() != null) {
				usuarioPlugin = usuarioPluginService.get(request.getIdusuarioplugin());
				if (usuarioPlugin == null) {
					Respuesta respuesta = Respuesta.internalNotFound();
					respuesta.setMessage("El usuario-plugin " + request.getIdusuarioplugin() + " no existe");
					return respuesta;
				}
				if (usuarioPlugin.getUsuario() == null
						|| !usuario.getId().equals(usuarioPlugin.getUsuario().getId())) {
					Respuesta respuesta = Respuesta.internalForbidden();
					respuesta.setMessage("El plugin instalado no pertenece al usuario autenticado");
					return respuesta;
				}
			}

			Boton boton = new Boton();
			boton.setCnombre(request.getCnombre().trim());
			boton.setCcolor(request.getCcolor().trim());
			boton.setCicono(request.getCicono());
			boton.setPerfil(perfil);
			boton.setUsuarioPlugin(usuarioPlugin);
			boton.setCparametro(request.getCparametro());

			Boton guardado = botonService.save(boton);

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Botón registrado correctamente");
			respuesta.setData(new BotonResponse(guardado));
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo registrar el botón: " + e.getMessage());
			return respuesta;
		}
	}

	@Override
	public Respuesta actualizar(Integer id, BotonRequest request) {
		try {
			Usuario usuario = tokenUtil.obtenerUsuarioDelToken();
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			Boton boton = botonService.get(id);
			if (boton == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El botón " + id + " no existe");
				return respuesta;
			}

			Perfil perfilActual = boton.getPerfil();
			if (perfilActual == null || perfilActual.getUsuario() == null
					|| !usuario.getId().equals(perfilActual.getUsuario().getId())) {
				Respuesta respuesta = Respuesta.internalForbidden();
				respuesta.setMessage("No tienes permiso para actualizar este botón");
				return respuesta;
			}

			Perfil perfil = perfilService.get(request.getIdperfil());
			if (perfil == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El perfil " + request.getIdperfil() + " no existe");
				return respuesta;
			}

			if (perfil.getUsuario() == null || !usuario.getId().equals(perfil.getUsuario().getId())) {
				Respuesta respuesta = Respuesta.internalForbidden();
				respuesta.setMessage("No tienes permiso para asignar este perfil al botón");
				return respuesta;
			}

			UsuarioPlugin usuarioPlugin = null;
			if (request.getIdusuarioplugin() != null) {
				usuarioPlugin = usuarioPluginService.get(request.getIdusuarioplugin());
				if (usuarioPlugin == null) {
					Respuesta respuesta = Respuesta.internalNotFound();
					respuesta.setMessage("El usuario-plugin " + request.getIdusuarioplugin() + " no existe");
					return respuesta;
				}
				if (usuarioPlugin.getUsuario() == null
						|| !usuario.getId().equals(usuarioPlugin.getUsuario().getId())) {
					Respuesta respuesta = Respuesta.internalForbidden();
					respuesta.setMessage("El plugin instalado no pertenece al usuario autenticado");
					return respuesta;
				}
			}

			boton.setCnombre(request.getCnombre().trim());
			boton.setCcolor(request.getCcolor().trim());
			boton.setCicono(request.getCicono());
			boton.setPerfil(perfil);
			boton.setUsuarioPlugin(usuarioPlugin);
			boton.setCparametro(request.getCparametro());

			Boton actualizado = botonService.save(boton);

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Botón actualizado correctamente");
			respuesta.setData(new BotonResponse(actualizado));
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo actualizar el botón: " + e.getMessage());
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

			Boton boton = botonService.get(id);
			if (boton == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El botón " + id + " no existe");
				return respuesta;
			}

			Perfil perfil = boton.getPerfil();
			if (perfil == null || perfil.getUsuario() == null
					|| !usuario.getId().equals(perfil.getUsuario().getId())) {
				Respuesta respuesta = Respuesta.internalForbidden();
				respuesta.setMessage("No tienes permiso para eliminar este botón");
				return respuesta;
			}

			botonService.delete(boton.getId());

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Botón eliminado correctamente");
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo eliminar el botón: " + e.getMessage());
			return respuesta;
		}
	}
}
