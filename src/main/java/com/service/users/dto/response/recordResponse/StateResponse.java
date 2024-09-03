/**
 * author @bhupendrasambare
 * Date   :02/09/24
 * Time   :2:17 am
 * Project:user service
 **/
package com.service.users.dto.response.recordResponse;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record StateResponse(
        Long id,
        @NotNull(message = "name is required")
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        @NotNull(message = "countryId is required")
        Long countryId,
        String countryName
) {

}
