package no.kdrs.grouse.utils;

import no.kdrs.grouse.utils.exception.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.*;
/**
 * This is an implementation of a global exception handler extending the
 * ResponseEntityExceptionHandler in order to gain control status codes and
 * response bodies that will be sent to the client. This allows us to give
 * more useful information back to the client
 * Taken from baeldung, need to check reference
 */

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    // 4XX range of status codes
    // 400 Bad Requests
    @Override
    protected ResponseEntity<Object>
    handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                     HttpHeaders headers, HttpStatus status,
                                     WebRequest request) {
        logger.error("400 Status Code: " + ex.getMessage());
        return handleExceptionInternal(ex, message(status, ex, request),
                headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            final HttpMessageNotReadableException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        logger.error("400 Status Code: " + ex.getMessage());
        return handleExceptionInternal(ex, message(status, ex, request),
                headers, BAD_REQUEST, request);
    }

    // If there is a problem with incoming arguments i.e null value where
    // value is required
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex, final HttpHeaders headers,
            final HttpStatus status, final WebRequest request) {
        logger.error("400 Status Code: " + ex.getMessage());
        return handleExceptionInternal(ex, message(status, ex, request), headers,
                BAD_REQUEST, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class,
            BadRequestException.class, ConstraintViolationException.class})
    public ResponseEntity<Object> handleBadRequest(
            final DataIntegrityViolationException ex,
            final WebRequest request) {
        return handleExceptionInternal(ex,
                message(BAD_REQUEST, ex, request), new HttpHeaders(),
                BAD_REQUEST, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleNotAutorised(
            final RuntimeException ex,
            final WebRequest request) {
        return handleExceptionInternal(ex,
                message(UNAUTHORIZED, ex, request), new HttpHeaders(),
                UNAUTHORIZED, request);
    }

    // 500
    @ExceptionHandler({NullPointerException.class, NoSuchMethodException.class,
            IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Object> handleInternal(final RuntimeException ex,
                                                 final WebRequest request) {
        logger.error("500 Status Code: " + ex.getMessage());
        return handleExceptionInternal(ex, message(INTERNAL_SERVER_ERROR, ex,
                request), new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
    }

    // 404
    @ExceptionHandler({EmptyResultDataAccessException.class,
            EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFound(
            final RuntimeException ex, final WebRequest request) {
        logger.error("404 Status Code: " + ex.getMessage());
        return handleExceptionInternal(ex, message(NOT_FOUND, ex, request),
                new HttpHeaders(), NOT_FOUND, request);
    }

    private ApiError message(final HttpStatus httpStatus, final Exception ex,
                             final WebRequest request) {
        final String message = ex.getMessage() == null ?
                ex.getClass().getSimpleName() : ex.getMessage();
        final String devMessage = ex.toString();
        final String devStackTrace = ex.toString();
        return new ApiError(httpStatus.value(), message, devMessage,
                devStackTrace, ((ServletWebRequest) request).getRequest()
                .getRequestURI());
    }
}
