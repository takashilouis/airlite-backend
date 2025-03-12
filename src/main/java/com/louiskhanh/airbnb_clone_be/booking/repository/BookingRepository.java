package com.louiskhanh.airbnb_clone_be.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.louiskhanh.airbnb_clone_be.booking.domain.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    
}