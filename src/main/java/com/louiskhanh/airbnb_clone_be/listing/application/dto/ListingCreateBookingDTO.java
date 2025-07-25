package com.louiskhanh.airbnb_clone_be.listing.application.dto;

import java.util.UUID;

import com.louiskhanh.airbnb_clone_be.listing.application.dto.vo.PriceVO;

public record ListingCreateBookingDTO (
    UUID listingPublicId, PriceVO price){
}
