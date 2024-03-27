package com.example.grpctask.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public record BookRequest(
        @NotBlank(message = "Title cannot be blank")
        String title,

        @NotBlank(message = "Author cannot be blank")
        String author,

        @Pattern(regexp = "\\d{3}-\\d{10}", message = "ISBN must be in the format '###-##########'")
        String isbn,

        @NotNull(message = "Quantity cannot be null")
        @PositiveOrZero(message = "Quantity must be a positive number or zero")
        Integer quantity) {
}
