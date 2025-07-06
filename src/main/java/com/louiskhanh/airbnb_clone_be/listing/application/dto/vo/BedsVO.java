package com.louiskhanh.airbnb_clone_be.listing.application.dto.vo;
import jakarta.validation.constraints.NotNull;

public record BedsVO (@NotNull(message = "Bed value is required") int value)
{}
