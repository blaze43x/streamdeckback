package com.streamdeck.streamdeck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streamdeck.streamdeck.model.Accion;

public interface AccionRepository extends JpaRepository<Accion, Integer> {
}
