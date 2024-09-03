package com.service.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressRequest(
        @NotBlank(message = "Landmark is required")
        @Size(max = 100, message = "Landmark must not exceed 100 characters")
        String landmark,

        @NotBlank(message = "Street is required")
        @Size(max = 100, message = "Street must not exceed 100 characters")
        String street,
        String city,
        String state,
        String postalCode,
        String country,
        Long cityId
) {}
