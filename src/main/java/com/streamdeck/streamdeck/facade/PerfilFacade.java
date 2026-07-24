package com.streamdeck.streamdeck.facade;

import com.streamdeck.streamdeck.dto.Respuesta;

public interface PerfilFacade {

	/**
	 * Lista los perfiles asociados a un usuario.
	 * @param idUsuario id del usuario
	 * @return Respuesta con la lista de perfiles en data
	 */
	Respuesta listarPorUsuario(Integer idUsuario);
}
