package com.louiskhanh.airbnb_clone_be.listing.application.dto;

import com.louiskhanh.airbnb_clone_be.listing.application.dto.vo.PriceVO;
import com.louiskhanh.airbnb_clone_be.listing.domain.BookingCategory;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.sub.PictureDTO;

import java.util.UUID;
public record DisplayCardListingDTO(PriceVO price,
                                    String location,
                                    PictureDTO cover,
                                    BookingCategory bookingCategory,
                                    UUID publicId) {
}