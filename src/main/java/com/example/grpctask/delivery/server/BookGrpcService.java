package com.example.grpctask.delivery.server;

import com.example.grpctask.Book;
import com.example.grpctask.BookMapper;
import com.example.grpctask.CreateBookRequest;
import com.example.grpctask.DeleteBookRequest;
import com.example.grpctask.DeleteBookResponse;
import com.example.grpctask.ReactorBookServiceGrpc;
import com.example.grpctask.ReadBookRequest;
import com.example.grpctask.UpdateBookRequest;
import com.example.grpctask.interceptors.CustomServerInterceptor;
import com.example.grpctask.services.BookService;
import com.google.protobuf.Empty;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@GrpcService(interceptors = {CustomServerInterceptor.class})
@RequiredArgsConstructor
public class BookGrpcService extends ReactorBookServiceGrpc.BookServiceImplBase {
    private final BookService service;
    private final Validator validator;
    private final BookMapper bookMapper = BookMapper.INSTANCE;
    @Override
    public Mono<Book> addNewBook(Mono<CreateBookRequest> request) {
        return request.flatMap(req ->
                service.addNewBook(validate(bookMapper.mapCreateBookReqToBookRequest(req)))
                        .map(bookMapper::mapBookResponseToBook));
    }
    @Override
    public Mono<Book> getBookById(Mono<ReadBookRequest> request){
        return request.flatMap(readBookRequest -> service.getBookById(UUID.fromString(readBookRequest.getId())))
                .map(bookMapper::mapBookResponseToBook);
    }
    @Override
    public Mono<Book> updateBook(Mono<UpdateBookRequest> request){
        return request.flatMap(updateBookRequest ->
                service.updateBookEntity(UUID.fromString(updateBookRequest.getId()),
                        bookMapper.mapBookToBookRequest(updateBookRequest.getBook()))
                        .map(bookMapper::mapBookResponseToBook));
    }
    @Override
    public Mono<DeleteBookResponse> deleteBook(Mono<DeleteBookRequest> request){
        return request.flatMap(deleteBookRequest ->
                service.delete(UUID.fromString(deleteBookRequest.getId()))
                        .map(aBoolean -> DeleteBookResponse.newBuilder()
                                .setSuccess(aBoolean)
                                .build()));
    }
    @Override
    public Flux<Book> getAllBooks(Mono<Empty> request) {
        return request.flatMapMany(book-> service.getAllBooks())
                .map(bookMapper::mapBookResponseToBook);
    }
    private <T> T validate(T data) {
        var errors = validator.validate(data);
        if (!errors.isEmpty()) throw new ConstraintViolationException(errors);
        return data;
    }
}
