package com.deivid.microservices.handler;

import com.deivid.microservices.model.exception.ResponseException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseException> handleException(final Exception exception) {
        log.error(exception.getMessage(), exception);

        return ResponseEntity.internalServerError().body(construirResponseError(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno"));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseException> handleNoResourceFoundException(final NoResourceFoundException exception) {
        log.error(exception.getMessage(), exception);

        return new ResponseEntity<>(construirResponseError(HttpStatus.NOT_FOUND, "Servicio no encontrado"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseException> handleConstraintViolationException(final ConstraintViolationException exception) {
        log.error(exception.getMessage(), exception);

        String messageError = exception
                .getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(construirResponseError(HttpStatus.BAD_REQUEST, messageError));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ResponseException> handleDuplicateKeyException(final DuplicateKeyException exception) {
        log.error(exception.getMessage(), exception);

        String messageError = "Violación de clave única en la base de datos.";

        String message = exception.getMessage();
        Pattern pattern =Pattern.compile("index: (\\w+) dup key");
        Matcher matcher = pattern.matcher(message);
        String fieldName = matcher.find() ? matcher.group(1) : null;

        if (!Objects.isNull(fieldName)) {
            messageError = "El valor de '" + fieldName + "' ya está en uso.";
        }

        ResponseException responseException = construirResponseError(HttpStatus.BAD_REQUEST, messageError);

        return ResponseEntity.badRequest().body(responseException);
    }

    private ResponseException construirResponseError(HttpStatus httpStatus, String mensaje) {
        return new ResponseException(httpStatus.value(), mensaje, httpStatus.getReasonPhrase());
    }

}
