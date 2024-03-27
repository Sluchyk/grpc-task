package com.example.grpctask.services;

import com.example.grpctask.dto.BookRequest;
import com.example.grpctask.dto.BookResponse;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Mono<BookResponse> addNewBook(BookRequest book);
    Mono<BookResponse> getBookById(UUID id);
    Mono<Boolean> delete(UUID id);
    Flux<BookResponse> getAllBooks();
    Mono<BookResponse> updateBookEntity(UUID id,BookRequest bookRequest);
}
