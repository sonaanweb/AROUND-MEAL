package com.lucky.around.meal.controller.response;

import java.time.LocalDateTime;

public record RatingResponseDto(
    Long RatingId,
    Long memberId,
    String restaurantId,
    Integer score,
    String content,
    LocalDateTime createAt) {}