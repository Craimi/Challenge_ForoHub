package com.ambystudio.forohub.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.lang.NullPointerException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class Errors {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity Error404(){
        return ResponseEntity.notFound().build();
    }

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity ErrorEnumIncompleto(HttpMessageNotReadableException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getLocalizedMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> ErrorJSONFaltante(NullPointerException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El JSON entrante debe incluir el parametro `actualizarUsuario` aun si este se encuentra vacio");
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> ErrorSinValor(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No existe el ID");
    }

    private record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
