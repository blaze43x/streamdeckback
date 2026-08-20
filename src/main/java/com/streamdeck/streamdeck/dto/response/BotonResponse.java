package com.streamdeck.streamdeck.dto.response;

import com.streamdeck.streamdeck.model.Boton;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotonResponse {

	private Integer id;
	private String cnombre;
	private String ccolor;
	private String cicono;

	public BotonResponse(Boton boton) {
		this.id = boton.getId();
		this.cnombre = boton.getCnombre();
		this.ccolor = boton.getCcolor();
		this.cicono = boton.getCicono();
	}
}
