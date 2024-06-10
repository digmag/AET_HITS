package ru.hits.common.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "ru.hits")
@Component
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(ForbiddenException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 403);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(UnauthorizedException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 401);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
