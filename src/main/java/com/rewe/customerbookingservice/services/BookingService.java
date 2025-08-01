package com.rewe.customerbookingservice.services;

import com.rewe.customerbookingservice.dtos.BookingDTO;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    List<BookingDTO> findAllBookings();

    BookingDTO findBookingById(Long id);

    BookingDTO saveBooking(BookingDTO booking);

    BookingDTO updateBooking(Long id, BookingDTO bookingDetails);

    boolean deleteBooking(Long id);

    List<BookingDTO> findBookingsByCustomerId(Long customerId);

    List<BookingDTO> findBookingsByBrandId(Long brandId);
}
