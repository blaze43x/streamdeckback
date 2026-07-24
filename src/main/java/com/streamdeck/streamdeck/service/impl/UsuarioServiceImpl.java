package com.streamdeck.streamdeck.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.streamdeck.streamdeck.model.Usuario;
import com.streamdeck.streamdeck.repository.UsuarioRepository;
import com.streamdeck.streamdeck.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;

	@Override
	public List<Usuario> all() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario get(Integer id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	@Override
	public Usuario save(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public void delete(Integer id) {
		usuarioRepository.deleteById(id);
	}
}
