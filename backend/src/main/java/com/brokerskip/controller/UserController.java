package com.brokerskip.controller;

import com.brokerskip.dto.ApiResponse;
import com.brokerskip.dto.UserDTO;
import com.brokerskip.dto.UserProfileRequest;
import com.brokerskip.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse<UserDTO>> createProfile(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody UserProfileRequest request) {
        log.info("Creating user profile");
        // Extract userId from token (in production)
        Integer userId = 1; // Placeholder
        UserDTO userDTO = userService.createUserProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.success("Profile created successfully", userDTO));
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserProfile(@PathVariable Integer userId) {
        log.info("Fetching profile for userId: {}", userId);
        UserDTO userDTO = userService.getUserProfile(userId);
        return ResponseEntity.ok(ApiResponse.success("Profile fetched successfully", userDTO));
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> updateProfile(
            @PathVariable Integer userId,
            @Valid @RequestBody UserProfileRequest request) {
        log.info("Updating profile for userId: {}", userId);
        UserDTO userDTO = userService.updateUserProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", userDTO));
    }
}
