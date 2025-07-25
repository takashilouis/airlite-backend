package com.louiskhanh.airbnb_clone_be.booking.application.dto;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;

public record BookedDateDTO(
        @NotNull OffsetDateTime startDate,
        @NotNull OffsetDateTime endDate
) {
}