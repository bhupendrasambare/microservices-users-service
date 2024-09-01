package com.service.users.dto.response.recordResponse;

import java.time.LocalDateTime;

public record CityResponse(Long id,
                           String name,
                           String pinCode,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt,
                           Long stateId,
                           String stateName,
                           Long countryId,
                           String countryName) {
}
