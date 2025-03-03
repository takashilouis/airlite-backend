package com.louiskhanh.airbnb_clone_be.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.louiskhanh.airbnb_clone_be.listing.domain.ListingPicture;

public interface ListingPictureRepository extends JpaRepository<ListingPicture, Long> {
    
}
