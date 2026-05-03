package com.petproject.itmoacsbackend.users.controllers;

import com.petproject.itmoacsbackend.users.dto.UserResponse;
import com.petproject.itmoacsbackend.users.dto.UserUpdateRequest;
import com.petproject.itmoacsbackend.users.entities.UserEntity;
import com.petproject.itmoacsbackend.users.repositories.UserRepository;
import com.petproject.itmoacsbackend.users.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me")
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<UserResponse> getCurrentUser(
            @AuthenticationPrincipal UserEntity user
    ) {
        UserResponse response = userService.getCurrentUser(user);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<UserResponse> updateCurrentUser(
            @AuthenticationPrincipal UserEntity user,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        UserResponse response = userService.updateUser(request, user);
        return ResponseEntity.ok(response);
    }

    // TODO: Доделать
    @GetMapping("/bookings")
    public ResponseEntity<?> getBookings(
            @AuthenticationPrincipal UserEntity user
    ) {
        return null;
    }

    // TODO: Доделать
    @GetMapping("/properties")
    public ResponseEntity<?> getProperties(
            @AuthenticationPrincipal UserEntity user
    ) {
        return null;
    }

}
