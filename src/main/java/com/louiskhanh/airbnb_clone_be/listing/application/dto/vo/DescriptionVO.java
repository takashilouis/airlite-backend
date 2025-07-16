package com.louiskhanh.airbnb_clone_be.listing.application.dto.vo;
import jakarta.validation.constraints.NotNull;

public record DescriptionVO (@NotNull(message = "Description value is required!") String value)
{}
