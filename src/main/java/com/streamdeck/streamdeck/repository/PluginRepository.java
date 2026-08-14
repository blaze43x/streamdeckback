package com.streamdeck.streamdeck.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.streamdeck.streamdeck.model.Plugin;

public interface PluginRepository extends JpaRepository<Plugin, Integer> {

	List<Plugin> findByAutor_Id(Integer idAutor);
}
