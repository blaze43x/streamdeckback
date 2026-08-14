package com.streamdeck.streamdeck.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.streamdeck.streamdeck.model.Boton;
import com.streamdeck.streamdeck.repository.BotonRepository;
import com.streamdeck.streamdeck.service.BotonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BotonServiceImpl implements BotonService {

	private final BotonRepository botonRepository;

	@Override
	public List<Boton> all() {
		return botonRepository.findAll();
	}

	@Override
	public Boton get(Integer id) {
		return botonRepository.findById(id).orElse(null);
	}

	@Override
	public Boton save(Boton boton) {
		return botonRepository.save(boton);
	}

	@Override
	public void delete(Integer id) {
		botonRepository.deleteById(id);
	}

	@Override
	public List<Boton> listarPorPerfil(Integer idPerfil) {
		return botonRepository.findByPerfil_Id(idPerfil);
	}

	@Override
	public List<Boton> listarPorUsuarioPlugin(Integer idUsuarioPlugin) {
		return botonRepository.findByUsuarioPlugin_Id(idUsuarioPlugin);
	}
}
