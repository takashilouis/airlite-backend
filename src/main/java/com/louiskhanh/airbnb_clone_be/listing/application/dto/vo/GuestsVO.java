package com.louiskhanh.airbnb_clone_be.listing.application.dto.vo;
import jakarta.validation.constraints.NotNull;

public record GuestsVO (@NotNull(message = "Guest value is required") int value)
{}
