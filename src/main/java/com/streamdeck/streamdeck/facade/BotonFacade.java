package com.streamdeck.streamdeck.facade;

import com.streamdeck.streamdeck.dto.Respuesta;

public interface BotonFacade {

	/**
	 * Lista los botones asociados a un perfil.
	 * @param idPerfil id del perfil
	 * @return Respuesta con la lista de botones en data
	 */
	Respuesta listarPorPerfil(Integer idPerfil);

	/**
	 * Ejecuta la acción asociada a un botón según su tipo.
	 * @param idBoton id del botón
	 * @return Respuesta del resultado de la operación
	 */
	Respuesta ejecutarAccion(Integer idBoton);
}
