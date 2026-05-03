package com.petproject.itmoacsbackend.booking.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record BookingCreateRequest(
        @NotEmpty
        LocalDate startDate,
        @NotEmpty
        LocalDate endDate,
        @Positive
        Double totalPrice,
        @Positive
        Integer guestsCount,
        String details
) {
}
