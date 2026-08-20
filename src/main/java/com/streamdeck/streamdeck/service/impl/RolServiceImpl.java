package com.streamdeck.streamdeck.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.streamdeck.streamdeck.model.Rol;
import com.streamdeck.streamdeck.repository.RolRepository;
import com.streamdeck.streamdeck.service.RolService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

	private final RolRepository rolRepository;

	@Override
	public List<Rol> all() {
		return rolRepository.findAll();
	}

	@Override
	public Rol get(Integer id) {
		return rolRepository.findById(id).orElse(null);
	}

	@Override
	public Rol save(Rol rol) {
		return rolRepository.save(rol);
	}

	@Override
	public void delete(Integer id) {
		rolRepository.deleteById(id);
	}
}
