package com.streamdeck.streamdeck.facade;

import com.streamdeck.streamdeck.dto.request.BotonRequest;
import com.streamdeck.streamdeck.dto.response.Respuesta;

public interface BotonFacade {

	Respuesta listarPorPerfil(Integer idPerfil);

	Respuesta obtenerPorId(Integer id);

	Respuesta registrar(BotonRequest request);

	Respuesta actualizar(Integer id, BotonRequest request);

	Respuesta eliminar(Integer id);
}
