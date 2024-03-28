package com.example.grpctask.delivery.client;

import com.example.grpctask.dto.BookRequest;
import com.example.grpctask.dto.BookResponse;
import com.example.grpctask.services.BookService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookRestController {
    private final BookService service;
    @PostMapping
    public Mono<ResponseEntity<BookResponse>> addNewBook(@RequestBody @Valid BookRequest request){
        return service.addNewBook(request).map(bookResponse ->
                ResponseEntity.status(HttpStatus.CREATED)
                        .body(bookResponse));
    }
    @GetMapping("/{id}")
    public Mono<ResponseEntity<BookResponse>> getBookById(@PathVariable String id){
        return service.getBookById(UUID.fromString(id))
                .map(ResponseEntity::ok);
    }
    @GetMapping
    public  Flux<ResponseEntity<BookResponse>> getAllBooks(){
        return service.getAllBooks()
                .map(ResponseEntity::ok);
    }
    @PutMapping("/{id}")
    public Mono<ResponseEntity<BookResponse>> updateBook(@PathVariable String id, @RequestBody @Valid BookRequest request){
        return service.updateBookEntity(UUID.fromString(id),request)
                .map(ResponseEntity::ok);
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Boolean>> deleteBook(@PathVariable String id){
        return service.delete(UUID.fromString(id))
                .map(ResponseEntity::ok);
    }
}
