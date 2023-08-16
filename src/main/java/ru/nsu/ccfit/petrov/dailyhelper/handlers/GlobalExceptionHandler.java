package ru.nsu.ccfit.petrov.dailyhelper.handlers;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.nsu.ccfit.petrov.dailyhelper.models.dtos.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
        HttpServletRequest request, HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(new ErrorResponse("Method not supported", e.getLocalizedMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(HttpServletRequest request,
                                                                         UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(new ErrorResponse(e.getLocalizedMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(HttpServletRequest request,
                                                                       NoHandlerFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponse("Endpoint not found", e.getLocalizedMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
        HttpServletRequest request, HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("JSON format not valid", e.getLocalizedMessage()));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledException(HttpServletRequest request,
                                                                 DisabledException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(new ErrorResponse(e.getLocalizedMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
        HttpServletRequest request, MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                               .getFieldErrors()
                               .stream()
                               .map(DefaultMessageSourceResolvable::getDefaultMessage)
                               .toList();
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("JSON format is not valid", e.getLocalizedMessage(), errors));
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ErrorResponse> handleMailException(HttpServletRequest request,
                                                             MailException e) {
        return ResponseEntity.badRequest()
                             .body(new ErrorResponse("Failed to send email", e.getLocalizedMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
        HttpServletRequest request, MissingServletRequestParameterException e) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("Missing required request parameter", e.getLocalizedMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherException(HttpServletRequest request,
                                                              Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Internal server error";

        // With ResponseStatus annotation
        if (e.getClass().isAnnotationPresent(ResponseStatus.class)) {
            ResponseStatus responseStatus = e.getClass().getAnnotation(ResponseStatus.class);
            status = responseStatus.code();
            message = e.getLocalizedMessage();
        }

        return ResponseEntity.status(status)
                             .body(new ErrorResponse(message, e.getLocalizedMessage()));
    }
}
