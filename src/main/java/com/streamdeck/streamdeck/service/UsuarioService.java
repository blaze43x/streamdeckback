package com.streamdeck.streamdeck.service;

import java.util.List;

import com.streamdeck.streamdeck.model.Usuario;

public interface UsuarioService {

	List<Usuario> all();

	Usuario get(Integer id);

	Usuario getByCorreo(String correo);

	Usuario save(Usuario usuario);

	void delete(Integer id);
}
