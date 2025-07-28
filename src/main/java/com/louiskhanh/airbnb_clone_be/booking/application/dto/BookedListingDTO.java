package com.louiskhanh.airbnb_clone_be.booking.application.dto;

import java.util.UUID;

import com.louiskhanh.airbnb_clone_be.listing.application.dto.sub.PictureDTO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.vo.PriceVO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookedListingDTO(@Valid PictureDTO cover,
                               @NotEmpty String location,
                               @Valid BookedDateDTO dates,
                               @Valid PriceVO totalPrice,
                               @NotNull UUID bookingPublicId,
                               @NotNull UUID listingPublicId) {
}