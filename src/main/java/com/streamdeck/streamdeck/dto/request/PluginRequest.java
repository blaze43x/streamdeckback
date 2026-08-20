package com.streamdeck.streamdeck.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PluginRequest {

	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 50, message = "El nombre no puede superar 50 caracteres")
	private String cnombre;

	private String cdescripcion;

	private String cparametro;
}
