package com.streamdeck.streamdeck.service;

import java.util.List;

import com.streamdeck.streamdeck.model.Rol;

public interface RolService {

	List<Rol> all();

	Rol get(Integer id);

	Rol save(Rol rol);

	void delete(Integer id);
}
