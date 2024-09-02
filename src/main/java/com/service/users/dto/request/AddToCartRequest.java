package com.service.users.dto.request;

import jakarta.validation.constraints.NotNull;

public record AddToCartRequest(@NotNull Long productId,
                               @NotNull Long quantity
) {}