package com.louiskhanh.airbnb_clone_be.listing.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ListingPictureMapper.class})
public class ListingPictureMapper {

}
