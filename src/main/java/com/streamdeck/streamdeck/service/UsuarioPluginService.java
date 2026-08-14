package com.streamdeck.streamdeck.service;

import java.util.List;

import com.streamdeck.streamdeck.model.UsuarioPlugin;

public interface UsuarioPluginService {

	List<UsuarioPlugin> all();

	UsuarioPlugin get(Integer id);

	UsuarioPlugin save(UsuarioPlugin usuarioPlugin);

	void delete(Integer id);

	List<UsuarioPlugin> listarPorUsuario(Integer idUsuario);

	List<UsuarioPlugin> listarPorPlugin(Integer idPlugin);

	UsuarioPlugin getByUsuarioAndPlugin(Integer idUsuario, Integer idPlugin);
}
