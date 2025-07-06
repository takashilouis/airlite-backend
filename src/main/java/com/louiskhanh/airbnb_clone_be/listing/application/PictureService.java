package com.louiskhanh.airbnb_clone_be.listing.application;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.louiskhanh.airbnb_clone_be.listing.application.dto.sub.PictureDTO;
import com.louiskhanh.airbnb_clone_be.listing.domain.Listing;
import com.louiskhanh.airbnb_clone_be.listing.domain.ListingPicture;
import com.louiskhanh.airbnb_clone_be.listing.mapper.ListingPictureMapper;
import com.louiskhanh.airbnb_clone_be.listing.repository.ListingPictureRepository;

@Service
public class PictureService {
    private final ListingPictureRepository listingPictureRepository;

    private final ListingPictureMapper listingPictureMapper;

    public PictureService(ListingPictureRepository listingPictureRepository, ListingPictureMapper listingPictureMapper){
        this.listingPictureRepository = listingPictureRepository;
        this.listingPictureMapper = listingPictureMapper;
    }

    public List<PictureDTO> saveAll(List<PictureDTO> pictures, Listing listing){
        Set<ListingPicture> listingPictures = listingPictureMapper.pictureDTOToListingPicture(pictures);

        boolean isFirst = true;
        for(ListingPicture listingPicture: listingPictures){
            listingPicture.setListing(listing);
            listingPicture.setCover(isFirst);
            isFirst = false;
            
        }

        listingPictureRepository.saveAll(listingPictures);
        return listingPictureMapper.listingPictureToPictureDTO(listingPictures.stream().toList());
    }
}
