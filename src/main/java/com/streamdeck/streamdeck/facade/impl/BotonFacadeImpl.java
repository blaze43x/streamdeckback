package com.streamdeck.streamdeck.facade.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.streamdeck.streamdeck.dto.Respuesta;
import com.streamdeck.streamdeck.dto.response.ResponseBoton;
import com.streamdeck.streamdeck.facade.BotonFacade;
import com.streamdeck.streamdeck.model.Boton;
import com.streamdeck.streamdeck.model.TipoAccion;
import com.streamdeck.streamdeck.service.BotonService;
import com.streamdeck.streamdeck.service.PerfilService;
import com.streamdeck.streamdeck.util.obs.ObsAcciones;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BotonFacadeImpl implements BotonFacade {

	private final BotonService botonService;
	private final PerfilService perfilService;
	private final ObsAcciones obsAcciones;

	@Override
	public Respuesta listarPorPerfil(Integer idPerfil) {
		try {
			if (perfilService.get(idPerfil) == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El perfil " + idPerfil + " no existe");
				return respuesta;
			}

			List<ResponseBoton> botones = botonService.listarPorPerfil(idPerfil).stream()
					.map(boton -> new ResponseBoton(
							boton.getId(),
							boton.getCnombre(),
							boton.getCcolor(),
							boton.getCicono(),
							boton.getTipoAccion() != null ? boton.getTipoAccion().getId() : null,
							boton.getCaccion()))
					.collect(Collectors.toList());

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
	@Transactional(readOnly = true)
	public Respuesta ejecutarAccion(Integer idBoton) {
		try {
			Boton boton = botonService.get(idBoton);
			if (boton == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El botón " + idBoton + " no existe");
				return respuesta;
			}

			if (!Boolean.TRUE.equals(boton.getBactivo())) {
				Respuesta respuesta = Respuesta.internalBadRequest();
				respuesta.setMessage("El botón " + idBoton + " no está activo");
				return respuesta;
			}

			if (boton.getTipoAccion() == null || boton.getTipoAccion().getId() == null) {
				Respuesta respuesta = Respuesta.internalBadRequest();
				respuesta.setMessage("El botón no tiene un tipo de acción configurado");
				return respuesta;
			}

			String valor = boton.getCaccion();
			if (valor == null || valor.isBlank()) {
				Respuesta respuesta = Respuesta.internalBadRequest();
				respuesta.setMessage("El botón no tiene una acción configurada");
				return respuesta;
			}

			int idTipo = boton.getTipoAccion().getId();
			if (idTipo == TipoAccion.CAMBIO_ESCENA) {
				return obsAcciones.cambiarEscena(valor.trim());
			}
			if (idTipo == TipoAccion.ABRIR_APLICACION) {
				return obsAcciones.abrirAplicacion(valor.trim());
			}
			if (idTipo == TipoAccion.ABRIR_PAGINA) {
				return obsAcciones.abrirAplicacion(valor.trim());
			}
			if (idTipo == TipoAccion.REPRODUCIR_SONIDO) {
				return obsAcciones.reproducirFuente(valor.trim());
			}
			if (idTipo == TipoAccion.COMANDO_CMD) {
				return obsAcciones.ejecutarComando(valor);
			}

			Respuesta respuesta = Respuesta.internalBadRequest();
			respuesta.setMessage("Tipo de acción no soportado: " + idTipo);
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo ejecutar la acción: " + e.getMessage());
			return respuesta;
		}
	}
}
