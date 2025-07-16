package com.louiskhanh.airbnb_clone_be.listing.application.dto.vo;
import jakarta.validation.constraints.NotNull;

public record TitleVO (@NotNull(message = "Title value is required") String value)
{}
