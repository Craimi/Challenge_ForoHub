package com.ambystudio.forohub.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Errors {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity Error400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(
                DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(
                errores
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity ErrorDuplicados(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Uno o más campos únicos ya existen en la base de datos. Verifica los datos ingresados.");
    }

    private record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
