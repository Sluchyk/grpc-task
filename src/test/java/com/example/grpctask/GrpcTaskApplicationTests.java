package com.example.grpctask;

import com.example.grpctask.dto.BookRequest;
import com.example.grpctask.dto.BookResponse;
import com.example.grpctask.exceptions.BookNotFoundException;
import com.example.grpctask.repository.BookEntity;
import com.example.grpctask.repository.BookRepository;
import com.example.grpctask.services.BookServiceImpl;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class GrpcTaskApplicationTests {

	@Test
	void contextLoads() {
	}
    @Mock
	private BookRepository bookRepository;
	@Mock
	private BookMapper mapper;
	private BookServiceImpl bookService;

	@BeforeEach
	void setUp() {
		bookRepository = mock(BookRepository.class);
		bookService = new BookServiceImpl(bookRepository);
	}

	@Test
	void testAddNewBook() {
		UUID id = UUID.randomUUID();
		BookRequest bookRequest = new BookRequest("Test Book", "Test Author", "123-1234567890", 1);
		BookEntity bookEntity = new BookEntity(id, "Test Book", "Test Author", "123-1234567890", 1);
		BookResponse bookResponse = new BookResponse(id, "Test Book", "Test Author", "123-1234567890", 1);

		when(bookRepository.save(any(BookEntity.class))).thenReturn(Mono.just(bookEntity));
		Mono<BookResponse> result = bookService.addNewBook(bookRequest);
		StepVerifier.create(result)
				.expectNextMatches(response -> response.id().equals(bookResponse.id()))
				.verifyComplete();
	}

	@Test
	void testGetBookById() {
		UUID id = UUID.randomUUID();
		BookEntity bookEntity = new BookEntity(id,"Test Book", "Test Author", "1234567890", 1);
		BookResponse bookResponse = new BookResponse(id,"Test Book", "Test Author", "1234567890", 1);
		when(bookRepository.findById(id)).thenReturn(Mono.just(bookEntity));
		when(mapper.bookEntityToBookResponse(bookEntity)).thenReturn(bookResponse);

		StepVerifier.create(bookService.getBookById(id))
				.expectNext(bookResponse)
				.verifyComplete();
	}

	@Test
	void testDeleteBook() {
		UUID id = UUID.randomUUID();
		when(bookRepository.existsById(id)).thenReturn(Mono.just(true));
		when(bookRepository.deleteById(id)).thenReturn(Mono.empty());

		StepVerifier.create(bookService.delete(id))
				.expectNext(true)
				.verifyComplete();
	}

	@Test
	void testDeleteNonExistingBook() {
		UUID id = UUID.randomUUID();
		when(bookRepository.existsById(id)).thenReturn(Mono.just(false));
		StepVerifier.create(bookService.delete(id))
				.expectError(BookNotFoundException.class)
				.verify();
	}

	@Test
	void testGetAllBooks() {
		UUID id2 = UUID.randomUUID();
		UUID id1 = UUID.randomUUID();
		BookEntity bookEntity1 = new BookEntity(id1,"Test Book1", "Test Author1", "1234567890", 1);
		BookEntity bookEntity2 = new BookEntity(id2,"Test Book2", "Test Author2", "2234567890", 1);
		BookResponse bookResponse1 = new BookResponse(id1,"Test Book1", "Test Author1", "1234567890", 1);
		BookResponse bookResponse2 = new BookResponse(id2,"Test Book2", "Test Author2", "2234567890", 1);
		when(bookRepository.findAll()).thenReturn(Flux.just(bookEntity1, bookEntity2));
		when(mapper.bookEntityToBookResponse(bookEntity1)).thenReturn(bookResponse1);
		when(mapper.bookEntityToBookResponse(bookEntity2)).thenReturn(bookResponse2);

		StepVerifier.create(bookService.getAllBooks())
				.expectNext(bookResponse1, bookResponse2)
				.verifyComplete();
	}

	@Test
	void testUpdateBookEntity() {
		UUID id = UUID.randomUUID();
		BookRequest bookRequest = new BookRequest("Update Book", "Update Author", "00000000", 1);
		BookEntity bookEntity = new BookEntity(id,"Test Book", "Test Author", "1234567890", 1);
		when(bookRepository.findById(id)).thenReturn(Mono.just(bookEntity));
		when(bookRepository.save(bookEntity)).thenReturn(Mono.just(bookEntity));
		BookResponse bookResponse = new BookResponse(id,"Update Book", "Update Author", "00000000", 1);
		when(mapper.bookEntityToBookResponse(bookEntity)).thenReturn(bookResponse);

		StepVerifier.create(bookService.updateBookEntity(id, bookRequest))
				.expectNext(bookResponse)
				.verifyComplete();
	}

}
