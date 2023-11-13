package com.globallogic.users.infrastructure.exceptions;

import com.globallogic.users.domain.exceptions.TokenException;
import com.globallogic.users.domain.exceptions.EmailExistsException;
import com.globallogic.users.domain.exceptions.UserNotFoundException;
import com.globallogic.users.domain.models.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(Throwable.class)
    public final ResponseEntity<ErrorResponse> handle(Throwable ex) {
        log.error("An exception occurred:", ex);

        Error error = new Error(LocalDateTime.now(), ErrorCodes.UNKNOWN_ERROR.getCodigo(), "Internal Server Error");

        return new ResponseEntity<>(new ErrorResponse(Collections.singletonList(error)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex) {

        List<Error> errors = ex.getBindingResult().getFieldErrors().stream().map(
                x -> new Error(LocalDateTime.now(), ErrorCodes.BAD_REQUEST_ERROR.getCodigo(), x.getField() + ": " + x.getDefaultMessage())
        ).collect(Collectors.toList());

        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailExistsException.class)
    public final ResponseEntity<ErrorResponse> handle(EmailExistsException ex) {
        Error error = new Error(LocalDateTime.now(), ErrorCodes.DUPLICATE_EMAIL_ERROR.getCodigo(), ex.getLocalizedMessage());

        return new ResponseEntity<>(new ErrorResponse(Collections.singletonList(error)), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenException.class)
    public final ResponseEntity<ErrorResponse> handle(TokenException ex) {
        Error error = new Error(LocalDateTime.now(), ErrorCodes.TOKEN_ERROR.getCodigo(), ex.getLocalizedMessage());

        return new ResponseEntity<>(new ErrorResponse(Collections.singletonList(error)), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handle(UserNotFoundException ex) {
        Error error = new Error(LocalDateTime.now(), ErrorCodes.USER_NOT_FOUND_ERROR.getCodigo(), ex.getLocalizedMessage());

        return new ResponseEntity<>(new ErrorResponse(Collections.singletonList(error)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException ex) {

        Error error = new Error(LocalDateTime.now(), ErrorCodes.BAD_REQUEST_ERROR.getCodigo(), ex.getLocalizedMessage());

        return new ResponseEntity<>(new ErrorResponse(Collections.singletonList(error)), HttpStatus.BAD_REQUEST);
    }
}
