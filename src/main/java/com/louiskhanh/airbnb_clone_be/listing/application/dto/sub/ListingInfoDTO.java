package com.louiskhanh.airbnb_clone_be.listing.application.dto.sub;

import com.louiskhanh.airbnb_clone_be.listing.application.dto.vo.BathsVO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.vo.BedroomsVO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.vo.BedsVO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.vo.GuestsVO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record ListingInfoDTO (
    @NotNull @Valid GuestsVO guests,
    @NotNull @Valid BedroomsVO bedrooms,
    @NotNull @Valid BathsVO baths,
    @NotNull @Valid BedsVO beds
){}
