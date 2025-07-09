package com.louiskhanh.airbnb_clone_be.listing.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.louiskhanh.airbnb_clone_be.listing.application.dto.sub.PictureDTO;
import com.louiskhanh.airbnb_clone_be.listing.domain.ListingPicture;

@Mapper(componentModel = "spring")
public interface ListingPictureMapper{
    
    @Mapping(target = "listingId", ignore = true)
    @Mapping(target = "listing", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Set<ListingPicture> pictureDTOToListingPicture(List<PictureDTO> pictureDTO);
    
    List<PictureDTO> listingPictureToPictureDTO(List<ListingPicture> listingPictures);

    @Mapping(target = "isCover", source = "cover")
    PictureDTO convertToPictureDTO(ListingPicture listingPicture);

    @Named("extract-cover")
    default PictureDTO extractCover(Set<ListingPicture> pictures){
        return pictures.stream().findFirst().map(this::convertToPictureDTO).orElseThrow();
    }
} 