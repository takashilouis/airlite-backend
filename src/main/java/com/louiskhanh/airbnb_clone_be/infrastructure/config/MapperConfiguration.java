package com.louiskhanh.airbnb_clone_be.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.louiskhanh.airbnb_clone_be.listing.mapper.ListingMapper;
import com.louiskhanh.airbnb_clone_be.listing.mapper.ListingMapperImpl;
import com.louiskhanh.airbnb_clone_be.listing.mapper.ListingPictureMapper;
import com.louiskhanh.airbnb_clone_be.listing.mapper.ListingPictureMapperImpl;

@Configuration
public class MapperConfiguration {

    @Bean
    public ListingMapper listingMapper() {
        return new ListingMapperImpl();
    }

    @Bean
    public ListingPictureMapper listingPictureMapper() {
        return new ListingPictureMapperImpl();
    }
} 