package com.louiskhanh.airbnb_clone_be.listing.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.louiskhanh.airbnb_clone_be.listing.application.dto.CreatedListingDTO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.DisplayCardListingDTO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.ListingCreateBookingDTO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.SaveListingDTO;
import com.louiskhanh.airbnb_clone_be.listing.domain.Listing;
import com.louiskhanh.airbnb_clone_be.listing.mapper.ListingMapper;
import com.louiskhanh.airbnb_clone_be.listing.repository.ListingRepository;
import com.louiskhanh.airbnb_clone_be.sharedkernel.service.State;
import com.louiskhanh.airbnb_clone_be.user.application.Auth0Service;
import com.louiskhanh.airbnb_clone_be.user.application.UserService;
import com.louiskhanh.airbnb_clone_be.user.application.dto.ReadUserDTO;

@Service
public class LandlordService {
    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;
    private final Auth0Service auth0Service;
    private final UserService userService;
    private final PictureService pictureService;
    
    public LandlordService(ListingRepository listingRepository, ListingMapper listingMapper, UserService userService, Auth0Service auth0Service, PictureService pictureService)
    {
        this.listingRepository = listingRepository;
        this.listingMapper = listingMapper;
        this.userService = userService;
        this.auth0Service = auth0Service;
        this.pictureService = pictureService;
    }
    
    public CreatedListingDTO create(SaveListingDTO saveListingDTO){
        Listing newListing = listingMapper.saveListingDTOToListing(saveListingDTO);

        ReadUserDTO userConnected = userService.getAuthenticatedUserFromSecurityContext();
        newListing.setLandlordPublicId(userConnected.publicId());
        newListing.setPublicId(UUID.randomUUID());
        
        Listing savedListing = listingRepository.saveAndFlush(newListing);

        pictureService.saveAll(saveListingDTO.getPictures(), savedListing);

        auth0Service.addLandlordRoleToUser(userConnected);

        return listingMapper.listingToCreatedListingDTO(savedListing);
    }

    @Transactional(readOnly = true)
    public List<DisplayCardListingDTO> getAllProperties(ReadUserDTO landlord) {
        List<Listing> properties = listingRepository.findAllByLandlordPublicIdFetchCoverPicture(landlord.publicId());
        return listingMapper.listingToDisplayCardListingDTOs(properties);
    }

    @Transactional
    public State<UUID, String> delete(UUID publicId, ReadUserDTO landlord) {
        long deletedSuccessfuly = listingRepository.deleteByPublicIdAndLandlordPublicId(publicId, landlord.publicId());
        if (deletedSuccessfuly > 0) {
            return State.<UUID, String>builder().forSuccess(publicId);
        } else {
            return State.<UUID, String>builder().forUnauthorized("User not authorized to delete this listing");
        }
    }

    public Optional<ListingCreateBookingDTO> getByListingPublicId(UUID publicId) {
        return listingRepository.findByPublicId(publicId).map(listingMapper::mapListingToListingCreateBookingDTO);
    }

    public List<DisplayCardListingDTO> getCardDisplayByListingPublicId(List<UUID> allListingPublicIDs) {
        return listingRepository.findAllByPublicIdIn(allListingPublicIDs)
                .stream()
                .map(listingMapper::listingToDisplayCardListingDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<DisplayCardListingDTO> getByPublicIdAndLandlordPublicId(UUID listingPublicId, UUID landlordPublicId) {
        return listingRepository.findOneByPublicIdAndLandlordPublicId(listingPublicId, landlordPublicId)
                .map(listingMapper::listingToDisplayCardListingDTO);
    }

}
