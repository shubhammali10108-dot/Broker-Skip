package com.brokerskip.controller;

import com.brokerskip.dto.ApiResponse;
import com.brokerskip.dto.OtpRequest;
import com.brokerskip.dto.OtpResponse;
import com.brokerskip.dto.OtpVerifyRequest;
import com.brokerskip.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/send-otp")
    public ResponseEntity<OtpResponse> sendOtp(@Valid @RequestBody OtpRequest request) {
        log.info("Received request to send OTP to: {}", request.getPhoneNumber());
        OtpResponse response = authService.sendOtp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<OtpResponse> verifyOtp(@Valid @RequestBody OtpVerifyRequest request) {
        log.info("Received request to verify OTP for: {}", request.getPhoneNumber());
        OtpResponse response = authService.verifyOtp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<?>> logout(@RequestHeader("Authorization") String token) {
        log.info("User logout request received");
        authService.logout(token);
        return ResponseEntity.ok(ApiResponse.success("Logged out successfully"));
    }
}
