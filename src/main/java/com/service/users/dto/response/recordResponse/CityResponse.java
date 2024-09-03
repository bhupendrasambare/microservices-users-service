package com.service.users.dto.response.recordResponse;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CityResponse(Long id,
                           @NotNull(message = "name is required")
                           String name,
                           @NotNull(message = "pinCode is required")
                           String pinCode,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt,
                           @NotNull(message = "stateId is required")
                           Long stateId,
                           String stateName,
                           Long countryId,
                           String countryName) {
}
