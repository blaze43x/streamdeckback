package com.streamdeck.streamdeck.service;

import java.util.List;

import com.streamdeck.streamdeck.model.Plugin;

public interface PluginService {

	List<Plugin> all();

	Plugin get(Integer id);

	Plugin save(Plugin plugin);

	void delete(Integer id);

	List<Plugin> listarPorAutor(Integer idAutor);
}
