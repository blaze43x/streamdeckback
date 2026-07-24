package com.streamdeck.streamdeck.service;

import java.util.List;

import com.streamdeck.streamdeck.model.Boton;

public interface BotonService {

	List<Boton> all();

	Boton get(Integer id);

	Boton save(Boton boton);

	void delete(Integer id);

	List<Boton> listarPorPerfil(Integer idPerfil);
}
