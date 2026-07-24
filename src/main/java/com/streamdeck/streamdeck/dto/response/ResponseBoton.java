package com.streamdeck.streamdeck.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBoton {

	private Integer id;
	private String cnombre;
	private String ccolor;
	private String cicono;
	private Integer idtipoaccion;
	private String caccion;
}
