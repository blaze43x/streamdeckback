package com.streamdeck.streamdeck.facade;

import com.streamdeck.streamdeck.dto.request.LoginRequest;
import com.streamdeck.streamdeck.dto.request.RegistroUsuarioRequest;
import com.streamdeck.streamdeck.dto.response.Respuesta;

public interface AuthFacade {

	Respuesta login(LoginRequest request);

	Respuesta registrar(RegistroUsuarioRequest request);
}
