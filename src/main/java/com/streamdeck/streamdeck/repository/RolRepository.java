package com.streamdeck.streamdeck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streamdeck.streamdeck.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}
