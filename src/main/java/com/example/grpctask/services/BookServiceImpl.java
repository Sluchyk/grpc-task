package com.example.grpctask.services;

import com.example.grpctask.BookMapper;
import com.example.grpctask.dto.BookRequest;
import com.example.grpctask.dto.BookResponse;
import com.example.grpctask.exceptions.BookNotFoundException;
import com.example.grpctask.exceptions.UniqueIsbException;
import com.example.grpctask.repository.BookRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper mapper = BookMapper.INSTANCE;
    @Override
    public Mono<BookResponse> addNewBook(BookRequest book) {
        return bookRepository.save(mapper.bookRequestToBookEntity(book))
                .onErrorMap(DuplicateKeyException.class,ex-> {
                    throw new UniqueIsbException("Book with isbn exists. Please enter another isbn");
                })
                .switchIfEmpty(Mono.error(() -> new RuntimeException("Can't save entity")))
                .map(mapper::bookEntityToBookResponse);
    }
    @Override
    public Mono<BookResponse> getBookById(UUID id) {
        return bookRepository.findById(id).map(mapper::bookEntityToBookResponse)
                .switchIfEmpty(Mono.error(()-> new BookNotFoundException("Book does not exist")));
    }

    @Override
    public Mono<Boolean> delete(UUID id) {
        return bookRepository.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return bookRepository.deleteById(id)
                                .thenReturn(true);
                    } else {
                        return Mono.error(()-> new BookNotFoundException("Book does not exist"));
                    }
                });
    }
    @Override
    public Flux<BookResponse> getAllBooks() {
        return bookRepository.findAll().map(mapper::bookEntityToBookResponse);
    }
    @Override
    public Mono<BookResponse> updateBookEntity(UUID id, BookRequest bookRequest) {
        return bookRepository.findById(id).flatMap(bookEntity -> {
                    bookEntity.setIsbn(bookRequest.isbn());
                    bookEntity.setAuthor(bookRequest.author());
                    bookEntity.setQuantity(bookRequest.quantity());
                    bookEntity.setTitle(bookRequest.title());
                    bookEntity.setId(id);
                    return bookRepository.save(bookEntity);
        })
                .map(mapper::bookEntityToBookResponse);
    }
}
