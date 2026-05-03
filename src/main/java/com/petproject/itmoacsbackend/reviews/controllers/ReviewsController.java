package com.petproject.itmoacsbackend.reviews.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/properties/{propertyId}/reviews")
public class ReviewsController {


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getReview(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        return null;
    }

    @GetMapping
    public ResponseEntity<?> getAllReviews(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/{id}/reviews")
    public ResponseEntity<?> createReview() {
        return null;
    }

}
