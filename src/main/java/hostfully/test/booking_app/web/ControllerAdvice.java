package hostfully.test.booking_app.web;

import hostfully.test.booking_app.domain.exceptions.OverlappingBookingException;
import hostfully.test.booking_app.domain.exceptions.entities.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(OverlappingBookingException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    ErrorResponse handleOverlappingBookingException(OverlappingBookingException ex, HttpServletRequest request){
        return new ErrorResponse(ex.toString(),
                "Cannot booking for the requested dates. There is either an overlapping with a booking or block",
                request.getRequestURI(),
                LocalDateTime.now().toString());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request){
        return new ErrorResponse(ex.getClass().getName(), ex.getMessage(), request.getRequestURI(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, HttpServletRequest request){
        return new ErrorResponse(EntityNotFoundException.class.getName(), ex.getMessage(), request.getRequestURI(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
        return new ErrorResponse(ex.getClass().getName(), ex.getMessage(), request.getRequestURI(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse handleRuntimeException(RuntimeException ex, HttpServletRequest request){
        return new ErrorResponse(ex.getClass().getName(), ex.getMessage(), request.getRequestURI(), LocalDateTime.now().toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){

        StringBuilder sb = new StringBuilder();
        sb.append("Validation failed for: [ ");
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            sb.append(String.format("%s: %s; ", fieldName, errorMessage));
        });
        sb.append(" ]");

        return new ErrorResponse(ex.getClass().getName(), sb.toString(), request.getRequestURI(), LocalDateTime.now().toString());
    }

    private record ErrorResponse(
            String exception, String errorMessage, String path, String timestamp
    ) {}
}
