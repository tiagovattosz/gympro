package br.edu.fema.gympro.exception.controller;

import br.edu.fema.gympro.exception.domain.*;
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

    @ExceptionHandler(ManutencaoNaoAceitaException.class)
    public ResponseEntity<ExcecaoPadrao> handleManutencaoNaoAceita(RuntimeException ex, HttpServletRequest request) {
        ExcecaoPadrao response = new ExcecaoPadrao(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(ClienteSemPlanoException.class)
    public ResponseEntity<ExcecaoPadrao> handleClienteSemPlano(RuntimeException ex, HttpServletRequest request) {
        ExcecaoPadrao response = new ExcecaoPadrao(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(AssinaturaVencidaException.class)
    public ResponseEntity<ExcecaoPadrao> handleAssinaturaVencida(RuntimeException ex, HttpServletRequest request) {
        ExcecaoPadrao response = new ExcecaoPadrao(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(InscricoesExcedidasException.class)
    public ResponseEntity<ExcecaoPadrao> handleInscricoesExcedidas(RuntimeException ex, HttpServletRequest request) {
        ExcecaoPadrao response = new ExcecaoPadrao(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(CpfDuplicadoException.class)
    public ResponseEntity<ExcecaoPadrao> handleCpfDuplicado(RuntimeException ex, HttpServletRequest request) {
        ExcecaoPadrao response = new ExcecaoPadrao(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(MenorDeIdadeException.class)
    public ResponseEntity<ExcecaoPadrao> handleMenorDeIdade(RuntimeException ex, HttpServletRequest request) {
        ExcecaoPadrao response = new ExcecaoPadrao(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExcecaoPadrao> handleDataIntegrityViolation(RuntimeException ex, HttpServletRequest request) {
        ExcecaoPadrao response = new ExcecaoPadrao(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExcecaoPadrao> handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request) {
        ExcecaoPadrao response = new ExcecaoPadrao(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
