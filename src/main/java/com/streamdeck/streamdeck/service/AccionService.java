package com.streamdeck.streamdeck.service;

import java.util.List;

import com.streamdeck.streamdeck.model.Accion;

public interface AccionService {

	List<Accion> all();

	Accion get(Integer id);

	Accion save(Accion accion);

	void delete(Integer id);
}
