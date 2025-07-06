package com.louiskhanh.airbnb_clone_be.listing.application.dto.sub;

import com.louiskhanh.airbnb_clone_be.listing.application.dto.vo.DescriptionVO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.vo.TitleVO;

import jakarta.validation.constraints.NotNull;

public record DescriptionDTO (
    @NotNull TitleVO title,
    @NotNull DescriptionVO description
){}
