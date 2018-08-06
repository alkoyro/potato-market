package com.epam.libg.exception.handling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * single entry point for exception handling
 */
@SuppressWarnings("unchecked")
@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
        LOGGER.error("Exception happened", ex);

        return super.handleExceptionInternal(ex,
                new ErrorResponse("Error", Collections.singletonList("Oops, something wrong on our side")),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        LOGGER.error("Error parsing request", ex);
        List<String> details = ex.getBindingResult().getAllErrors().
                stream().
                map(DefaultMessageSourceResolvable::getDefaultMessage).
                collect(Collectors.toList());
        ErrorResponse error = new ErrorResponse("Validation Failed", details);

        return super.handleExceptionInternal(ex,
                error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

}
