package com.example.grpctask.delivery.client;

import com.example.grpctask.dto.ErrorResponse;
import com.example.grpctask.exceptions.BookNotFoundException;
import com.example.grpctask.exceptions.UniqueIsbException;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@Slf4j
@RestControllerAdvice
public class BookGlobalExceptionHandler{
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBankAccountNotFoundException(BookNotFoundException ex) {
        var errorHttpResponseDto = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage(), LocalDateTime.now());
        log.error("(GlobalControllerAdvice) BookNotFoundException", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorHttpResponseDto);
    }
    @ExceptionHandler(UniqueIsbException.class)
    public ResponseEntity<ErrorResponse> handleUniqueIsbException(UniqueIsbException ex) {
        var errorHttpResponseDto = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage(), LocalDateTime.now());
        log.error("(GlobalControllerAdvice) UniqueIsbException", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorHttpResponseDto);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMessage = bindingResult.getFieldErrors().get(0).getDefaultMessage();
        log.error("(GlobalControllerAdvice) MethodArgumentNotValidException");
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException ex) {
        var errorHttpResponseDto = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), LocalDateTime.now());
        log.error("(GlobalControllerAdvice) DataAccessException", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorHttpResponseDto);
    }
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(WebExchangeBindException ex) {
        var errorHttpResponseDto = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage(), LocalDateTime.now());
        log.error("(GlobalControllerAdvice) InvalidAmountException", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorHttpResponseDto);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        var errorHttpResponseDto = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage(), LocalDateTime.now());
        log.error("(GlobalControllerAdvice) ConstraintViolationException", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorHttpResponseDto);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        var errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), LocalDateTime.now());
        log.error("(GlobalControllerAdvice) RuntimeException", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
}
