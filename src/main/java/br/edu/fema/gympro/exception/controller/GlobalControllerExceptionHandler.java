package br.edu.fema.gympro.exception.controller;

import br.edu.fema.gympro.exception.domain.ExcecaoPadrao;
import br.edu.fema.gympro.exception.domain.ObjetoNaoEncontrado;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ObjetoNaoEncontrado.class)
    public ResponseEntity<ExcecaoPadrao> handleNotFound(RuntimeException ex, HttpServletRequest request) {
        ExcecaoPadrao response = new ExcecaoPadrao(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExcecaoPadrao> handleDataIntegrityViolation(RuntimeException ex, HttpServletRequest request) {
        String msg = "Não foi possível realizar operação.";
        ExcecaoPadrao response = new ExcecaoPadrao(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getClass().getSimpleName(),
                msg,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExcecaoPadrao> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ExcecaoPadrao response = new ExcecaoPadrao(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getClass().getSimpleName(),
                Objects.requireNonNull(ex.getFieldError()).getField() + ": " + Objects.requireNonNull(ex.getFieldError().getDefaultMessage()),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
