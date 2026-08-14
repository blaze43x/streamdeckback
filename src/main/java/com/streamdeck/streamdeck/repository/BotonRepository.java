package com.streamdeck.streamdeck.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streamdeck.streamdeck.model.Boton;

public interface BotonRepository extends JpaRepository<Boton, Integer> {

	List<Boton> findByPerfil_Id(Integer idPerfil);

	List<Boton> findByUsuarioPlugin_Id(Integer idUsuarioPlugin);
}
