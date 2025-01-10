package com.ambystudio.forohub.infra;

public class ValidationException extends RuntimeException{

    public ValidationException(String mensaje) {
        super(mensaje);
    }
}
