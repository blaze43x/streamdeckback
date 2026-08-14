package com.streamdeck.streamdeck.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequest {

	private Integer id;

	@NotBlank(message = "El nombre es obligatorio")
	@Size(max = 100, message = "El nombre no puede superar 100 caracteres")
	private String cnombre;

	@NotBlank(message = "El correo es obligatorio")
	@Email(message = "El correo no es válido")
	@Size(max = 150, message = "El correo no puede superar 150 caracteres")
	private String ccorreo;
}
