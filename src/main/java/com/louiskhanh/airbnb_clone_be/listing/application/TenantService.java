package com.louiskhanh.airbnb_clone_be.listing.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.louiskhanh.airbnb_clone_be.listing.application.dto.DisplayCardListingDTO;
import com.louiskhanh.airbnb_clone_be.listing.domain.BookingCategory;
import com.louiskhanh.airbnb_clone_be.listing.domain.Listing;
import com.louiskhanh.airbnb_clone_be.listing.mapper.ListingMapper;
import com.louiskhanh.airbnb_clone_be.listing.repository.ListingRepository;
import com.louiskhanh.airbnb_clone_be.user.application.UserService;
import org.springframework.transaction.annotation.Transactional;
import com.louiskhanh.airbnb_clone_be.sharedkernel.service.State;
import java.util.Optional;
import java.util.UUID;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.DisplayListingDTO;
import com.louiskhanh.airbnb_clone_be.user.application.dto.ReadUserDTO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.sub.LandlordListingDTO;

@Service
public class TenantService {
    private final ListingRepository listingRepository;

    private final ListingMapper listingMapper;

    private final UserService userService;

    public TenantService(ListingRepository listingRepository, ListingMapper listingMapper, UserService userService) {
        this.listingRepository = listingRepository;
        this.listingMapper = listingMapper;
        this.userService = userService;
       //  this.bookingService = bookingService;
    }

    public Page<DisplayCardListingDTO> getAllByCategory(Pageable pageable, BookingCategory category) {
        Page<Listing> allOrBookingCategory;
        if (category == BookingCategory.ALL) {
            allOrBookingCategory = listingRepository.findAllWithCoverOnly(pageable);
        } else {
            allOrBookingCategory = listingRepository.findAllByBookingCategoryWithCoverOnly(pageable, category);
        }

        return allOrBookingCategory.map(listingMapper::listingToDisplayCardListingDTO);
    }


    @Transactional(readOnly = true)
    public State<DisplayListingDTO, String> getOne(UUID publicId) {
        Optional<Listing> listingByPublicIdOpt = listingRepository.findByPublicId(publicId);

        if (listingByPublicIdOpt.isEmpty()) {
            return State.<DisplayListingDTO, String>builder()
                    .forError(String.format("Listing doesn't exist for publicId: %s", publicId));
        }

        DisplayListingDTO displayListingDTO = listingMapper.listingToDisplayListingDTO(listingByPublicIdOpt.get());

        ReadUserDTO readUserDTO = userService.getByPublicId(listingByPublicIdOpt.get().getLandlordPublicId()).orElseThrow();
        LandlordListingDTO landlordListingDTO = new LandlordListingDTO(readUserDTO.firstName(), readUserDTO.imageUrl());
        displayListingDTO.setLandlord(landlordListingDTO);

        return State.<DisplayListingDTO, String>builder().forSuccess(displayListingDTO);
    }
}
