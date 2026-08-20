package com.streamdeck.streamdeck.facade.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.streamdeck.streamdeck.dto.request.PluginRequest;
import com.streamdeck.streamdeck.dto.response.PluginResponse;
import com.streamdeck.streamdeck.dto.response.Respuesta;
import com.streamdeck.streamdeck.facade.PluginFacade;
import com.streamdeck.streamdeck.model.Boton;
import com.streamdeck.streamdeck.model.Plugin;
import com.streamdeck.streamdeck.model.Usuario;
import com.streamdeck.streamdeck.model.UsuarioPlugin;
import com.streamdeck.streamdeck.service.BotonService;
import com.streamdeck.streamdeck.service.PluginService;
import com.streamdeck.streamdeck.service.UsuarioPluginService;
import com.streamdeck.streamdeck.util.PluginFileStorage;
import com.streamdeck.streamdeck.util.TokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PluginFacadeImpl implements PluginFacade {

	private final PluginService pluginService;
	private final UsuarioPluginService usuarioPluginService;
	private final BotonService botonService;
	private final TokenUtil tokenUtil;
	private final PluginFileStorage pluginFileStorage;

	@Override
	public Respuesta listar() {
		try {
			Usuario usuario = tokenUtil.obtenerUsuarioDelToken();
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			List<PluginResponse> plugins = pluginService.listarActivos().stream()
					.map(PluginResponse::new)
					.toList();

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Plugins obtenidos correctamente");
			respuesta.setData(plugins);
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudieron listar los plugins: " + e.getMessage());
			return respuesta;
		}
	}

	@Override
	public Respuesta listarPorAutor() {
		try {
			Usuario autor = tokenUtil.obtenerUsuarioDelToken();
			if (autor == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			List<PluginResponse> plugins = pluginService.listarPorAutorActivos(autor.getId()).stream()
					.map(PluginResponse::new)
					.toList();

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Plugins del autor obtenidos correctamente");
			respuesta.setData(plugins);
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudieron listar los plugins del autor: " + e.getMessage());
			return respuesta;
		}
	}

	@Override
	public Respuesta listarInstalados() {
		try {
			Usuario usuario = tokenUtil.obtenerUsuarioDelToken();
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			List<PluginResponse> plugins = usuarioPluginService.listarPorUsuario(usuario.getId()).stream()
					.map(UsuarioPlugin::getPlugin)
					.filter(plugin -> plugin != null)
					.map(PluginResponse::new)
					.toList();

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Plugins instalados obtenidos correctamente");
			respuesta.setData(plugins);
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudieron listar los plugins instalados: " + e.getMessage());
			return respuesta;
		}
	}

	@Override
	public Respuesta obtenerPorId(Integer id) {
		try {
			Usuario usuario = tokenUtil.obtenerUsuarioDelToken();
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			Plugin plugin = pluginService.get(id);
			if (plugin == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El plugin " + id + " no existe");
				return respuesta;
			}

			if (!Boolean.TRUE.equals(plugin.getBactivo())) {
				Respuesta respuesta = Respuesta.internalForbidden();
				respuesta.setMessage("El plugin ya no está activo y no se dará soporte de ello");
				return respuesta;
			}

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Plugin obtenido correctamente");
			respuesta.setData(new PluginResponse(plugin));
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo obtener el plugin: " + e.getMessage());
			return respuesta;
		}
	}

	@Override
	public Respuesta registrar(PluginRequest request, MultipartFile archivo) {
		try {
			Usuario autor = tokenUtil.obtenerUsuarioDelToken();
			if (autor == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			String ruta = pluginFileStorage.guardar(archivo);

			Plugin plugin = new Plugin();
			plugin.setCnombre(request.getCnombre().trim());
			plugin.setCdescripcion(request.getCdescripcion());
			plugin.setCparametro(request.getCparametro());
			plugin.setCruta(ruta);
			plugin.setAutor(autor);
			plugin.setBactivo(Boolean.TRUE);

			Plugin guardado = pluginService.save(plugin);

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Plugin registrado correctamente");
			respuesta.setData(new PluginResponse(guardado));
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo registrar el plugin: " + e.getMessage());
			return respuesta;
		}
	}

	@Override
	public Respuesta eliminar(Integer id) {
		try {
			Usuario usuario = tokenUtil.obtenerUsuarioDelToken();
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			Plugin plugin = pluginService.get(id);
			if (plugin == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El plugin " + id + " no existe");
				return respuesta;
			}

			if (plugin.getAutor() == null || !usuario.getId().equals(plugin.getAutor().getId())) {
				Respuesta respuesta = Respuesta.internalForbidden();
				respuesta.setMessage("Solo el autor puede desactivar este plugin");
				return respuesta;
			}

			if (!Boolean.TRUE.equals(plugin.getBactivo())) {
				Respuesta respuesta = Respuesta.internalBadRequest();
				respuesta.setMessage("El plugin ya se encuentra inactivo");
				return respuesta;
			}

			plugin.setBactivo(Boolean.FALSE);
			Plugin actualizado = pluginService.save(plugin);

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Plugin desactivado correctamente");
			respuesta.setData(new PluginResponse(actualizado));
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo desactivar el plugin: " + e.getMessage());
			return respuesta;
		}
	}

	@Override
	public Respuesta desinstalar(Integer id) {
		try {
			Usuario usuario = tokenUtil.obtenerUsuarioDelToken();
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return respuesta;
			}

			UsuarioPlugin usuarioPlugin = usuarioPluginService.getByUsuarioAndPlugin(usuario.getId(), id);
			if (usuarioPlugin == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El plugin " + id + " no está instalado para este usuario");
				return respuesta;
			}

			List<Boton> botones = botonService.listarPorUsuarioPlugin(usuarioPlugin.getId());
			for (Boton boton : botones) {
				boton.setUsuarioPlugin(null);
				botonService.save(boton);
			}

			usuarioPluginService.delete(usuarioPlugin.getId());

			Respuesta respuesta = Respuesta.ok();
			respuesta.setMessage("Plugin desinstalado correctamente");
			return respuesta;
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo desinstalar el plugin: " + e.getMessage());
			return respuesta;
		}
	}

	@Override
	public ResponseEntity<?> descargar(Integer id) {
		try {
			Usuario usuario = tokenUtil.obtenerUsuarioDelToken();
			if (usuario == null) {
				Respuesta respuesta = Respuesta.internalUnathorized();
				respuesta.setMessage("No se pudo identificar al usuario");
				return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
			}

			Plugin plugin = pluginService.get(id);
			if (plugin == null) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("El plugin " + id + " no existe");
				return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
			}

			if (!Boolean.TRUE.equals(plugin.getBactivo())) {
				Respuesta respuesta = Respuesta.internalForbidden();
				respuesta.setMessage("El plugin está inactivo y no se puede descargar");
				return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
			}

			UsuarioPlugin existente = usuarioPluginService.getByUsuarioAndPlugin(usuario.getId(), plugin.getId());
			if (existente == null) {
				UsuarioPlugin usuarioPlugin = new UsuarioPlugin();
				usuarioPlugin.setUsuario(usuario);
				usuarioPlugin.setPlugin(plugin);
				usuarioPluginService.save(usuarioPlugin);
			}

			Path ruta = pluginFileStorage.obtenerRutaAbsoluta(plugin.getCruta());
			if (!Files.exists(ruta) || !Files.isRegularFile(ruta)) {
				Respuesta respuesta = Respuesta.internalNotFound();
				respuesta.setMessage("No se encontró el archivo del plugin para descarga");
				return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
			}

			byte[] contenido = Files.readAllBytes(ruta);
			ByteArrayResource recurso = new ByteArrayResource(contenido);

			return ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.contentLength(contenido.length)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + ruta.getFileName().toString() + "\"")
					.body(recurso);
		} catch (Exception e) {
			Respuesta respuesta = Respuesta.internalServerError();
			respuesta.setMessage("No se pudo descargar el plugin: " + e.getMessage());
			return ResponseEntity.status(respuesta.getStatus()).body(respuesta);
		}
	}
}
