/**
 * author @bhupendrasambare
 * Date   :02/09/24
 * Time   :2:17 am
 * Project:user service
 **/
package com.service.users.dto.response.recordResponse;

import java.time.LocalDateTime;

public record StateResponse(
        Long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long countryId,
        String countryName
) {

}
