package com.example.grpctask.delivery.server;

import com.example.grpctask.exceptions.BookNotFoundException;
import com.example.grpctask.exceptions.UniqueIsbException;
import io.grpc.Status;
import io.grpc.StatusException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.support.WebExchangeBindException;

@Slf4j
@GrpcAdvice
public class BookAdviceControllerGrcp {
    @GrpcExceptionHandler(BookNotFoundException.class)
    public StatusException handleBookNotFoundException(BookNotFoundException ex) {
        var status = Status.NOT_FOUND.withDescription(ex.getLocalizedMessage()).withCause(ex);
        log.error("(GrpcExceptionAdvice) BookNotFoundException: ", ex);
        return status.asException();
    }
    @GrpcExceptionHandler(UniqueIsbException.class)
    public StatusException handleUniqueException(BookNotFoundException ex) {
        var status = Status.NOT_FOUND.withDescription(ex.getLocalizedMessage()).withCause(ex);
        log.error("(GrpcExceptionAdvice) : UniqueIsbException", ex);
        return status.asException();
    }

    @GrpcExceptionHandler(DataAccessException.class)
    public StatusException handleDataAccessException(DataAccessException ex) {
        var status = Status.INVALID_ARGUMENT.withDescription(ex.getLocalizedMessage()).withCause(ex);
        log.error("(GrpcExceptionAdvice) DataAccessException: ", ex);
        return status.asException();
    }

    @GrpcExceptionHandler(ConstraintViolationException.class)
    public StatusException handleConstraintViolationException(ConstraintViolationException ex) {
        var status = Status.INVALID_ARGUMENT.withDescription(ex.getLocalizedMessage()).withCause(ex);
        log.error("(GrpcExceptionAdvice) ConstraintViolationException: ", ex);
        return status.asException();
    }

    @GrpcExceptionHandler(MethodArgumentNotValidException.class)
    public StatusException handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var status = Status.INVALID_ARGUMENT.withDescription(ex.getLocalizedMessage()).withCause(ex);
        log.error("(GrpcExceptionAdvice) MethodArgumentNotValidException: ", ex);
        return status.asException();
    }

    @GrpcExceptionHandler(WebExchangeBindException.class)
    public StatusException handleWebExchangeBindException(WebExchangeBindException ex) {
        var status = Status.INVALID_ARGUMENT.withDescription(ex.getBindingResult()
                .getFieldErrors().get(0).getDefaultMessage());
        log.error("(GrpcExceptionAdvice) IllegalArgumentException: ", ex);
        return status.asException();
    }
    @GrpcExceptionHandler(RuntimeException.class)
    public StatusException handleRuntimeException(RuntimeException ex) {
        var status = Status.INTERNAL.withDescription(ex.getLocalizedMessage());
        log.error("(GrpcExceptionAdvice) RuntimeException: ", ex);
        return status.asException();
    }
}
