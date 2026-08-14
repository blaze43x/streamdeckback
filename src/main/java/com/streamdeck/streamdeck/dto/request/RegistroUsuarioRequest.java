package com.streamdeck.streamdeck.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistroUsuarioRequest {

	@NotNull(message = "Los datos del usuario son obligatorios")
	@Valid
	private UsuarioRequest usuario;

	@NotBlank(message = "La contraseña es obligatoria")
	private String password;
}
