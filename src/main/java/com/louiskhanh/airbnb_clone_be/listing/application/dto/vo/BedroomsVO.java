package com.louiskhanh.airbnb_clone_be.listing.application.dto.vo;
import jakarta.validation.constraints.NotNull;

public record BedroomsVO (@NotNull(message = "Bedroom value is required") int value)
{}
