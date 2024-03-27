package com.example.grpctask.repository;

import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BookRepository extends ReactiveCrudRepository<BookEntity, UUID> {
}
