package com.petproject.itmoacsbackend.payments.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentsController {

    @PostMapping
    public ResponseEntity<?> createPayment() {
        return null;
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyPayments() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPayment(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<?> payPayment(@PathVariable Long id) {
        return null;
    }

}
