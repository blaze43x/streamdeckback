package com.streamdeck.streamdeck.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BotonRequest {

	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 15, message = "El nombre no puede superar 15 caracteres")
	private String cnombre;

	@NotBlank(message = "El color es obligatorio")
	@Size(max = 7, message = "El color no puede superar 7 caracteres")
	private String ccolor;

	@NotBlank(message = "El icono es obligatorio")
	private String cicono;

	@NotNull(message = "El perfil es obligatorio")
	private Integer idperfil;

	private Integer idusuarioplugin;

	private String cparametro;
}
