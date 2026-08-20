package com.streamdeck.streamdeck.dto.response;

import com.streamdeck.streamdeck.model.Plugin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PluginResponse {

	private Integer id;
	private String cnombre;
	private String cdescripcion;

	public PluginResponse(Plugin plugin) {
		this.id = plugin.getId();
		this.cnombre = plugin.getCnombre();
		this.cdescripcion = plugin.getCdescripcion();
	}
}
