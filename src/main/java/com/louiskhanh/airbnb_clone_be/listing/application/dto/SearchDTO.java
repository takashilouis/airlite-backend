package com.louiskhanh.airbnb_clone_be.listing.application.dto;
import com.louiskhanh.airbnb_clone_be.booking.application.dto.BookedDateDTO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.sub.ListingInfoDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record SearchDTO(@Valid BookedDateDTO dates,
                        @Valid ListingInfoDTO infos,
                        @NotEmpty String location) {
}