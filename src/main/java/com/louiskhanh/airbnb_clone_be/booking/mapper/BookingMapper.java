package com.louiskhanh.airbnb_clone_be.booking.mapper;

import com.louiskhanh.airbnb_clone_be.booking.application.dto.BookedDateDTO;
import com.louiskhanh.airbnb_clone_be.booking.application.dto.NewBookingDTO;
import com.louiskhanh.airbnb_clone_be.booking.domain.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    Booking newBookingToBooking(NewBookingDTO newBookingDTO);

    BookedDateDTO bookingToCheckAvailability(Booking booking);
}