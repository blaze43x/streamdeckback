package com.streamdeck.streamdeck.service;

import java.util.List;

import com.streamdeck.streamdeck.model.TipoAccion;

public interface TipoAccionService {

	List<TipoAccion> all();

	TipoAccion get(Integer id);

	TipoAccion save(TipoAccion tipoAccion);

	void delete(Integer id);
}
