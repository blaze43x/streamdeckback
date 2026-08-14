package com.streamdeck.streamdeck.dto.response;

import com.streamdeck.streamdeck.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

	private Integer id;
	private String cnombre;
	private String ccorreo;

	public UsuarioResponse(Usuario usuario) {
		this.id = usuario.getId();
		this.cnombre = usuario.getCnombre();
		this.ccorreo = usuario.getCcorreo();
	}
}
