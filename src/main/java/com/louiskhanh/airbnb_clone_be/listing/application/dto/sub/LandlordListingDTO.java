package com.louiskhanh.airbnb_clone_be.listing.application.dto.sub;

import jakarta.validation.constraints.NotNull;

public record LandlordListingDTO (
    @NotNull String firstname,
    @NotNull String imageUrl
){}
