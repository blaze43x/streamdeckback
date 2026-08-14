package com.streamdeck.streamdeck.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streamdeck.streamdeck.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByCcorreo(String ccorreo);
}
