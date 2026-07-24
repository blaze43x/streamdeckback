package com.streamdeck.streamdeck.dto;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Respuesta {

    public static final int OK = HttpStatus.OK.value(); //Cuando la solicitud tuvo éxito y se logró realizar
    public static final int INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value(); //Cuando ocurre un error y no se puede manejar de ninguna manera
    public static final int NOT_FOUND = HttpStatus.NOT_FOUND.value(); //Cuando un recurso solicitado no se logra encontrar pero
    public static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value(); //Se usa cuando la solicitud del cliente contiene un error
    public static final int UNAUTHORIZED = HttpStatus.UNAUTHORIZED.value(); //Retorna una respuesta indicando que la solicitud no está autorizada.
    public static final int CONFLICT = HttpStatus.CONFLICT.value(); //Conflicto con alguna regla de negocio que impiden una operación
    public static final int FORBIDDEN = HttpStatus.FORBIDDEN.value(); //Cuando el usuario no tiene permiso o está bloqueado
    public static final int SERVICE_UNAVAILABLE = HttpStatus.SERVICE_UNAVAILABLE.value(); //Cuando la solicitud es válida pero no se puede procesar
    
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

    /**
     * Cuando la solicitud tuvo éxito y se logró realizar
     * la operación que el cliente solicitó
     * 
     * @return Una respuesta con este tipo de solicitud
     */
    public static Respuesta ok() {
        return new Respuesta(true, OK);
    }

    /**
     * Cuando ocurre un error que no se puede manejar de ninguna manera
     * y puede ser capturado por un catch u otro método
     * 
     * @return Una respuesta con este tipo de solicitud
     */
    public static Respuesta internalServerError() {
        return new Respuesta(false, INTERNAL_SERVER_ERROR);
    }

    /**
     * Cuando un recurso solicitado no se logra encontrar pero
     * puede estar disponible en un futuro
     * 
     * @return Una respuesta con este tipo de solicitud
     */
    public static Respuesta internalNotFound() {
        return new Respuesta(false, NOT_FOUND);
    }

    /**
     * Se usa cuando la solicitud del cliente contiene un error
     * como sintaxis malformada o solicitudes engañosasa
     * 
     * @return Una respuesta con este tipo de solicitud
     */
    public static Respuesta internalBadRequest() {
        return new Respuesta(false, BAD_REQUEST);
    }

    /**
     * Retorna una respuesta indicando que la solicitud no está autorizada.
     * 
     * @return Una respuesta con código HTTP 401 (Unauthorized)
     */
    public static Respuesta internalUnathorized() {
        return new Respuesta(false, UNAUTHORIZED);
    }

    /**
     * Retorna una respuesta indicando que el acceso a la solicitud es prohibido.
     * 
     * @return Una respuesta con código HTTP 403 (Forbidden)
     */
    public static Respuesta internalForbidden() {
        return new Respuesta(false, FORBIDDEN);
    }

    /**
     * Retorna una respuesta indicando que el recurso solicitado ya existe.
     * 
     * @return Una respuesta con código HTTP 409 (Conflict)
     */
    public static Respuesta internalConflict() {
        return new Respuesta(false, CONFLICT);
    }

    /**
     * Retorna una respuesta indicando que el servicio no está disponible.
     * 
     * @return Una respuesta con código HTTP 503 (Service Unavailable)
     */
    public static Respuesta internalServiceUnavailable() {
        return new Respuesta(false, SERVICE_UNAVAILABLE);
    }
}
