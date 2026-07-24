package com.streamdeck.streamdeck.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streamdeck.streamdeck.model.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

	List<Perfil> findByUsuario_Id(Integer idUsuario);
}
