package com.petproject.itmoacsbackend.booking.controllers;

import com.petproject.itmoacsbackend.users.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    @PostMapping
    public ResponseEntity<?> createBooking() {
        return null;
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyBookings(@AuthenticationPrincipal UserEntity user) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking(@PathVariable String id) {
        return null;
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable String id) {
        return null;
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<?> confirmBooking(@PathVariable String id) {
        return null;
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<?> rejectBooking(@PathVariable String id) {
        return null;
    }
}
