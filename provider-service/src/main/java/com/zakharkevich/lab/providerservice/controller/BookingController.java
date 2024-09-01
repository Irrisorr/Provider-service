package com.zakharkevich.lab.providerservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @GetMapping
    @PreAuthorize("hasAuthority('booking.read')")
    public List getAllBookings() {
        // TODO
        return null;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('booking.read')")
    public Object getBookingById(@PathVariable Long id) {
        // TODO
        return null;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('booking.write')")
    @ResponseStatus(HttpStatus.CREATED)
    public Object createBooking(@RequestBody Object bookingDto) {
        // TODO
        return null;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('booking.write')")
    public Object updateBooking(@PathVariable Long id, @RequestBody Object bookingDto) {
        // TODO
        return null;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('booking.write')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBooking(@PathVariable Long id) {
        // TODO
    }

    @GetMapping("/providers/{providerId}/services/{serviceId}/availability")
    @PreAuthorize("hasAuthority('booking.read')")
    public List getAvailableSlots(@PathVariable Long providerId, @PathVariable Long serviceId, @RequestParam LocalDate date) {
        // TODO
        return null;
    }
}