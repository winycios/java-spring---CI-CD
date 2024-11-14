package com.example.springcicd.controller.exceptions;


import com.example.springcicd.services.exceptions.DatabaseException;
import com.example.springcicd.services.exceptions.ResourceNotFound;
import com.example.springcicd.services.exceptions.ValidationExc;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

/**
 * Classe responsável por lidar com exceções globais na aplicação.
 * Utiliza a anotação {@code @ControllerAdvice} para centralizar o tratamento de exceções em controllers.
 */
@ControllerAdvice
public class ExceptionsHandler {

    // Excessão para recurso não encontrado
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ModeloError> resourceNotFound(ResourceNotFound e, HttpServletRequest request) {
        String error = "Recurso não encontrado";
        HttpStatus status = HttpStatus.NOT_FOUND;
        ModeloError err = new ModeloError(Instant.now(), status.value(), error, e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Excessão para validação
    @ExceptionHandler(ValidationExc.class)
    public ResponseEntity<ModeloError> resourceNotFound(ValidationExc e, HttpServletRequest request) {

        String error = "Erro de validação";
        HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
        ModeloError err = new ModeloError(Instant.now(), status.value(), error, e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    // Excessão de banco de dados
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ModeloError> resourceNotFound(DatabaseException e, HttpServletRequest request) {

        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ModeloError err = new ModeloError(Instant.now(), status.value(), error, e.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

}
