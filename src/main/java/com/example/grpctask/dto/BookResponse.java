package com.example.grpctask.dto;

import java.util.UUID;

public record BookResponse(UUID id,
                           String title,
                           String author,
                           String isbn,
                           Integer quantity) {
}
