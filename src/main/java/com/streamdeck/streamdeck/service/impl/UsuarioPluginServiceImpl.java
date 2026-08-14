package com.streamdeck.streamdeck.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.streamdeck.streamdeck.model.UsuarioPlugin;
import com.streamdeck.streamdeck.repository.UsuarioPluginRepository;
import com.streamdeck.streamdeck.service.UsuarioPluginService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioPluginServiceImpl implements UsuarioPluginService {

	private final UsuarioPluginRepository usuarioPluginRepository;

	@Override
	public List<UsuarioPlugin> all() {
		return usuarioPluginRepository.findAll();
	}

	@Override
	public UsuarioPlugin get(Integer id) {
		return usuarioPluginRepository.findById(id).orElse(null);
	}

	@Override
	public UsuarioPlugin save(UsuarioPlugin usuarioPlugin) {
		return usuarioPluginRepository.save(usuarioPlugin);
	}

	@Override
	public void delete(Integer id) {
		usuarioPluginRepository.deleteById(id);
	}

	@Override
	public List<UsuarioPlugin> listarPorUsuario(Integer idUsuario) {
		return usuarioPluginRepository.findByUsuario_Id(idUsuario);
	}

	@Override
	public List<UsuarioPlugin> listarPorPlugin(Integer idPlugin) {
		return usuarioPluginRepository.findByPlugin_Id(idPlugin);
	}

	@Override
	public UsuarioPlugin getByUsuarioAndPlugin(Integer idUsuario, Integer idPlugin) {
		return usuarioPluginRepository.findByUsuario_IdAndPlugin_Id(idUsuario, idPlugin).orElse(null);
	}
}
