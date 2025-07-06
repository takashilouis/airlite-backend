package com.louiskhanh.airbnb_clone_be.listing.application.dto.vo;
import jakarta.validation.constraints.NotNull;

public record BathsVO (@NotNull(message = "Bathroom value is required") int value)
{}
