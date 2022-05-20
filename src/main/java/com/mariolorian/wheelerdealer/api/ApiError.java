package com.mariolorian.wheelerdealer.api;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ApiError {

    int statusCode;
    LocalDateTime timestamp;
    String message;
    String description;

}
