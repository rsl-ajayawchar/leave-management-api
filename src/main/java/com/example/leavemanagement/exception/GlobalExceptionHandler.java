package com.example.leavemanagement.exception;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LeaveNotFoundException.class)
    public ResponseEntity<ApiError> notFound(LeaveNotFoundException ex){return error(HttpStatus.NOT_FOUND,ex.getMessage(),Map.of());}
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex){
        Map<String,String> errors=new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e->errors.put(e.getField(),e.getDefaultMessage()));
        ex.getBindingResult().getGlobalErrors().forEach(e->errors.put(e.getObjectName(),e.getDefaultMessage()));
        return error(HttpStatus.BAD_REQUEST,"Validation failed",errors);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> unreadable(HttpMessageNotReadableException ex){
        return error(HttpStatus.BAD_REQUEST,"Request contains invalid JSON, date, or enum value",Map.of());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> unexpected(Exception ex){
        return error(HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected error occurred",Map.of());
    }
    private ResponseEntity<ApiError> error(HttpStatus status,String message,Map<String,String> errors){
        return ResponseEntity.status(status).body(new ApiError(Instant.now(),status.value(),status.getReasonPhrase(),message,errors));
    }
}
