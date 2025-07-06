package com.louiskhanh.airbnb_clone_be.listing.application;

import org.springframework.stereotype.Service;

import com.louiskhanh.airbnb_clone_be.listing.application.dto.CreatedListingDTO;
import com.louiskhanh.airbnb_clone_be.listing.application.dto.SaveListingDTO;
import com.louiskhanh.airbnb_clone_be.listing.domain.Listing;
import com.louiskhanh.airbnb_clone_be.listing.mapper.ListingMapper;
import com.louiskhanh.airbnb_clone_be.listing.repository.ListingRepository;
import com.louiskhanh.airbnb_clone_be.user.application.Auth0Service;
import com.louiskhanh.airbnb_clone_be.user.application.UserService;
import com.louiskhanh.airbnb_clone_be.user.application.dto.ReadUserDTO;

@Service
public class LandlordService {
    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;
    private Auth0Service auth0Service;
    private UserService userService;
    private PictureService pictureService;
    
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
        
        Listing savedListing = listingRepository.saveAndFlush(newListing);

        pictureService.saveAll(saveListingDTO.getPictures(), savedListing);

        auth0Service.addLandlordRoleToUser(userConnected);

        return listingMapper.listingToCreatedListingDTO(savedListing);
    }
}
