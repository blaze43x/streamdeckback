package com.streamdeck.streamdeck.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.streamdeck.streamdeck.model.Plugin;
import com.streamdeck.streamdeck.repository.PluginRepository;
import com.streamdeck.streamdeck.service.PluginService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PluginServiceImpl implements PluginService {

	private final PluginRepository pluginRepository;

	@Override
	public List<Plugin> all() {
		return pluginRepository.findAll();
	}

	@Override
	public Plugin get(Integer id) {
		return pluginRepository.findById(id).orElse(null);
	}

	@Override
	public Plugin save(Plugin plugin) {
		return pluginRepository.save(plugin);
	}

	@Override
	public void delete(Integer id) {
		pluginRepository.deleteById(id);
	}

	@Override
	public List<Plugin> listarPorAutor(Integer idAutor) {
		return pluginRepository.findByAutor_Id(idAutor);
	}
}
