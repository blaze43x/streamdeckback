package com.streamdeck.streamdeck.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.streamdeck.streamdeck.model.TipoAccion;
import com.streamdeck.streamdeck.repository.TipoAccionRepository;
import com.streamdeck.streamdeck.service.TipoAccionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoAccionServiceImpl implements TipoAccionService {

	private final TipoAccionRepository tipoAccionRepository;

	@Override
	public List<TipoAccion> all() {
		return tipoAccionRepository.findAll();
	}

	@Override
	public TipoAccion get(Integer id) {
		return tipoAccionRepository.findById(id).orElse(null);
	}

	@Override
	public TipoAccion save(TipoAccion tipoAccion) {
		return tipoAccionRepository.save(tipoAccion);
	}

	@Override
	public void delete(Integer id) {
		tipoAccionRepository.deleteById(id);
	}
}
