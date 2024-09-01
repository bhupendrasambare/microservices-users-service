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

        @NotBlank(message = "City is required")
        @Size(max = 50, message = "City must not exceed 50 characters")
        String city,

        @NotBlank(message = "State is required")
        @Size(max = 50, message = "State must not exceed 50 characters")
        String state,

        @NotBlank(message = "Postal Code is required")
        @Size(max = 20, message = "Postal Code must not exceed 20 characters")
        String postalCode,

        @NotBlank(message = "Country is required")
        @Size(max = 50, message = "Country must not exceed 50 characters")
        String country,

        @NotNull(message = "User ID is required")
        Long userId,

        Long cityId
) {}
