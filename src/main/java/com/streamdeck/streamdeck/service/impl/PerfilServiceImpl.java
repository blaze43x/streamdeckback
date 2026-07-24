package com.streamdeck.streamdeck.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.streamdeck.streamdeck.model.Perfil;
import com.streamdeck.streamdeck.repository.PerfilRepository;
import com.streamdeck.streamdeck.service.PerfilService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements PerfilService {

	private final PerfilRepository perfilRepository;

	@Override
	public List<Perfil> all() {
		return perfilRepository.findAll();
	}

	@Override
	public Perfil get(Integer id) {
		return perfilRepository.findById(id).orElse(null);
	}

	@Override
	public Perfil save(Perfil perfil) {
		return perfilRepository.save(perfil);
	}

	@Override
	public void delete(Integer id) {
		perfilRepository.deleteById(id);
	}

	@Override
	public List<Perfil> listarPorUsuario(Integer idUsuario) {
		return perfilRepository.findByUsuario_Id(idUsuario);
	}
}
