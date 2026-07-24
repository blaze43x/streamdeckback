package com.streamdeck.streamdeck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streamdeck.streamdeck.model.TipoAccion;

public interface TipoAccionRepository extends JpaRepository<TipoAccion, Integer> {
}
