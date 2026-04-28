package com.petproject.itmoacsbackend.reviews.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewsController {

    @PostMapping
    public ResponseEntity<?> createReview() {
        return null;
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyReviews() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReview(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        return null;
    }
}
