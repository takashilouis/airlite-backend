package com.louiskhanh.airbnb_clone_be.listing.application.dto.vo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TitleVO (
    @NotNull(message = "Title value is required")
    @Size(max = 256, message = "Title must not exceed 256 characters")
    String value
){}
