package com.streamdeck.streamdeck.dto.response;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Respuesta {

	public static final int OK = HttpStatus.OK.value();
	public static final int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();
	public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value();
	public static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
	public static final int UNAUTHORIZED = HttpStatus.UNAUTHORIZED.value();
	public static final int CONFLICT = HttpStatus.CONFLICT.value();
	public static final int FORBIDDEN = HttpStatus.FORBIDDEN.value();
	public static final int SERVICE_UNAVAILABLE = HttpStatus.SERVICE_UNAVAILABLE.value();

	private Boolean success;
	private Integer status;
	private String message;
	private Object data;

	public Respuesta(Boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public Respuesta(Boolean success, Integer status) {
		this.success = success;
		this.status = status;
	}

	public Respuesta(Boolean success, Integer status, String message) {
		this.success = success;
		this.status = status;
		this.message = message;
	}

	public static Respuesta ok() {
		return new Respuesta(true, OK);
	}

	public static Respuesta internalServerError() {
		return new Respuesta(false, INTERNAL_SERVER_ERROR);
	}

	public static Respuesta internalNotFound() {
		return new Respuesta(false, NOT_FOUND);
	}

	public static Respuesta internalBadRequest() {
		return new Respuesta(false, BAD_REQUEST);
	}

	public static Respuesta internalUnathorized() {
		return new Respuesta(false, UNAUTHORIZED);
	}

	public static Respuesta internalForbidden() {
		return new Respuesta(false, FORBIDDEN);
	}

	public static Respuesta internalConflict() {
		return new Respuesta(false, CONFLICT);
	}

	public static Respuesta internalServiceUnavailable() {
		return new Respuesta(false, SERVICE_UNAVAILABLE);
	}
}
