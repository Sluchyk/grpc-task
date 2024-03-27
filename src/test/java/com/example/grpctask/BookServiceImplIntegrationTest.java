package com.example.grpctask;

import com.example.grpctask.dto.BookRequest;
import com.example.grpctask.dto.BookResponse;
import com.example.grpctask.exceptions.BookNotFoundException;
import com.example.grpctask.exceptions.UniqueIsbException;
import com.example.grpctask.repository.BookRepository;
import com.example.grpctask.services.BookServiceImpl;
import io.r2dbc.spi.ConnectionFactory;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// TODO run docker-compose before using this test!!!
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class BookServiceImplIntegrationTest {
    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper mapper;
    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14-alpine")
            .withDatabaseName("testdb")
            .withPassword("test")
            .withUsername("test");

    private UUID id2 = null;
    private final UUID notExistedId = UUID.randomUUID();
    private final BookRequest bookRequest = new BookRequest("Title1","Author1","123-1234567890",1);
    @BeforeAll
    void setUp() {
        postgreSQLContainer.start();
        id2 = bookRepository.save(mapper.bookRequestToBookEntity(bookRequest)).block().getId();
    }
    @TestConfiguration
    static
    class TestConfig {
        @Bean
        public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
            ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
            initializer.setConnectionFactory(connectionFactory);

            CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
            populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
            initializer.setDatabasePopulator(populator);

            return initializer;
        }
    }

    @AfterAll
    void tearDown() {
        bookRepository.deleteAll().block();
        postgreSQLContainer.stop();
    }

    @Test
    void testAddNewBook() {
        BookRequest bookRequest = new BookRequest("title","author","111-1111111",1);
        Mono<BookResponse> responseMono = bookService.addNewBook(bookRequest);
        Assertions.assertNotNull(responseMono.block());
    }
    @Test
    void testAddNewBook_negative() {
        BookRequest bookRequest = new BookRequest("title","author","123-1234567890",1);
        Mono<BookResponse> responseMono = bookService.addNewBook(bookRequest);
        Assertions.assertThrows(UniqueIsbException.class, responseMono::block);
    }

    @Test
    void testGetBookById() {
        Mono<BookResponse> responseMono = bookService.getBookById(id2);
        Assertions.assertEquals(id2, responseMono.map(BookResponse::id).block());
    }
    @Test
    void testGetBookById_negative() {
        Mono<BookResponse> responseMono = bookService.getBookById(notExistedId);
        Assertions.assertThrows(BookNotFoundException.class, responseMono::block);
    }

    @Test
    void testDelete() {
        Mono<Boolean> responseMono = bookService.delete(id2);
        Assertions.assertEquals(Boolean.TRUE, responseMono.block());
    }

    @Test
    void testGetAllBooks() {
        Flux<BookResponse> responseFlux = bookService.getAllBooks();
        Assertions.assertFalse(responseFlux.collectList().block().isEmpty());
    }

    @Test
    void testUpdateBookEntity() {
        BookRequest updatedBookRequest = new BookRequest("update","update","000-0000000000",1);
        Mono<BookResponse> responseMono = bookService.updateBookEntity(id2, updatedBookRequest);
        Assertions.assertNotNull(responseMono);
        Assertions.assertEquals(updatedBookRequest.author(), Objects.requireNonNull(responseMono.block()).author());
        Assertions.assertEquals(updatedBookRequest.title(), Objects.requireNonNull(responseMono.block()).title());
        Assertions.assertEquals(updatedBookRequest.isbn(), Objects.requireNonNull(responseMono.block()).isbn());
        Assertions.assertEquals(updatedBookRequest.quantity(), Objects.requireNonNull(responseMono.block()).quantity());

    }
}