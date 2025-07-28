package com.louiskhanh.airbnb_clone_be.listing.application.dto.vo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DescriptionVO (
    @NotNull(message = "Description value is required!")
    @Size(max = 512, message = "Description must not exceed 512 characters")
    String value
){}
