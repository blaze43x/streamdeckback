package com.streamdeck.streamdeck.dto.response;

import com.streamdeck.streamdeck.model.Perfil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilResponse {

	private Integer id;
	private String cnombre;

	public PerfilResponse(Perfil perfil) {
		this.id = perfil.getId();
		this.cnombre = perfil.getCnombre();
	}
}
