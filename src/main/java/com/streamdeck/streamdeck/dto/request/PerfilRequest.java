package com.streamdeck.streamdeck.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PerfilRequest {

	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 20, message = "El nombre no puede superar 20 caracteres")
	private String cnombre;
}
