package com.zakharkevich.lab.providerservice.service;

import com.zakharkevich.lab.providerservice.model.entity.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    List getAllBookings();

    Optional getBookingById(Long id);

    Booking createBooking(Booking booking);

    Booking updateBooking(Long id, Booking bookingDetails);

    void deleteBooking(Long id);

    List getAvailableSlots(Long providerId, Long serviceId, LocalDate date);

    boolean isSlotAvailable(Long providerId, Long serviceId, LocalDateTime visitTime);
}