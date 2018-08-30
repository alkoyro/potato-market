package com.epam.libg.exception.handling;

import com.epam.libg.model.ExpectedTypeFormat;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
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
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error("Exception happened", ex);

        List<String> details = new ArrayList<>();
        if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodParameter methodParameter = ((MethodArgumentTypeMismatchException) ex).getParameter();
            ExpectedTypeFormat expectedTypeFormat = methodParameter.getParameterAnnotation(ExpectedTypeFormat.class);
            if (expectedTypeFormat != null) {
                details.add(expectedTypeFormat.value() + " -> " + expectedTypeFormat.example());
            }
        }

        if (details.isEmpty()) {
            details = Collections.singletonList("Not expected request format");
        }

        return super.handleExceptionInternal(ex,
                new ErrorResponse("Error", details),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error("Exception happened", ex);

        List<String> details = new ArrayList<>();

        //looks for @ExpectedTypeFormat and collect detailed error messages if jackson exception happend
        if (ex.getCause() instanceof JsonMappingException) {
            List<JsonMappingException.Reference> references = ((JsonMappingException) ex.getCause()).getPath();

            //clean all refs that cannot be processed
            references = references.stream()
                    .filter(ref -> ref.getFrom() != null && ref.getFieldName() != null)
                    .collect(Collectors.toList());

            for (JsonMappingException.Reference reference : references) {
                String fieldName = reference.getFieldName();
                try {
                    Field field = reference.getFrom().getClass().getDeclaredField(fieldName);
                    if (field != null && field.getAnnotation(ExpectedTypeFormat.class) != null) {
                        ExpectedTypeFormat expectedTypeFormat = field.getAnnotation(ExpectedTypeFormat.class);
                        details.add(expectedTypeFormat.value() + " -> " + expectedTypeFormat.example());
                    }
                } catch (NoSuchFieldException e) {
                    LOGGER.error("Error retrieving field: " + e);
                }
            }
        }

        if (details.isEmpty()) {
            details = Collections.singletonList("Not expected request format");
        }

        return super.handleExceptionInternal(ex,
                new ErrorResponse("Error", details),
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
