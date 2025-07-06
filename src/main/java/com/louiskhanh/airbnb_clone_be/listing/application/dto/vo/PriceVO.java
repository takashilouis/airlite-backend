package com.louiskhanh.airbnb_clone_be.listing.application.dto.vo;
import jakarta.validation.constraints.NotNull;

public record PriceVO (@NotNull(message = "Price value is required") int value)
{}
