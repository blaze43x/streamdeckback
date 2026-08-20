package com.streamdeck.streamdeck.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.streamdeck.streamdeck.dto.request.PluginRequest;
import com.streamdeck.streamdeck.dto.response.Respuesta;

public interface PluginFacade {

	Respuesta listar();

	Respuesta listarPorAutor();

	Respuesta listarInstalados();

	Respuesta obtenerPorId(Integer id);

	Respuesta registrar(PluginRequest request, MultipartFile archivo);

	Respuesta eliminar(Integer id);

	Respuesta desinstalar(Integer id);

	ResponseEntity<?> descargar(Integer id);
}
