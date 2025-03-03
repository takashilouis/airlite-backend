package com.louiskhanh.airbnb_clone_be.listing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.louiskhanh.airbnb_clone_be.listing.domain.Listing;

public interface ListingRepository extends JpaRepository<Listing, Long>{
    
}