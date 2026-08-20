package com.streamdeck.streamdeck.facade;

import com.streamdeck.streamdeck.dto.request.PerfilRequest;
import com.streamdeck.streamdeck.dto.response.Respuesta;

public interface PerfilFacade {

	Respuesta listar();

	Respuesta actualizar(Integer id, PerfilRequest request);

	Respuesta eliminar(Integer id);
}
