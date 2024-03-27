package com.example.grpctask.dto;

import java.time.LocalDateTime;

public record ErrorResponse(Integer statusCode,
                            String message,
                            LocalDateTime time) {
}
