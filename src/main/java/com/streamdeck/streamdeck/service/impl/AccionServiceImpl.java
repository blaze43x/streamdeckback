package com.streamdeck.streamdeck.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.streamdeck.streamdeck.model.Accion;
import com.streamdeck.streamdeck.repository.AccionRepository;
import com.streamdeck.streamdeck.service.AccionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccionServiceImpl implements AccionService {

	private final AccionRepository accionRepository;

	@Override
	public List<Accion> all() {
		return accionRepository.findAll();
	}

	@Override
	public Accion get(Integer id) {
		return accionRepository.findById(id).orElse(null);
	}

	@Override
	public Accion save(Accion accion) {
		return accionRepository.save(accion);
	}

	@Override
	public void delete(Integer id) {
		accionRepository.deleteById(id);
	}
}
