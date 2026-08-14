package com.streamdeck.streamdeck.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streamdeck.streamdeck.model.UsuarioPlugin;

public interface UsuarioPluginRepository extends JpaRepository<UsuarioPlugin, Integer> {

	List<UsuarioPlugin> findByUsuario_Id(Integer idUsuario);

	List<UsuarioPlugin> findByPlugin_Id(Integer idPlugin);

	Optional<UsuarioPlugin> findByUsuario_IdAndPlugin_Id(Integer idUsuario, Integer idPlugin);
}
