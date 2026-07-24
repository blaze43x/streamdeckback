package com.streamdeck.streamdeck.service;

import java.util.List;

import com.streamdeck.streamdeck.model.Perfil;

public interface PerfilService {

	List<Perfil> all();

	Perfil get(Integer id);

	Perfil save(Perfil perfil);

	void delete(Integer id);

	List<Perfil> listarPorUsuario(Integer idUsuario);
}
