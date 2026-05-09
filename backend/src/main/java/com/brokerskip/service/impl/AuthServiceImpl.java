package com.brokerskip.service.impl;

import com.brokerskip.dto.OtpRequest;
import com.brokerskip.dto.OtpResponse;
import com.brokerskip.dto.OtpVerifyRequest;
import com.brokerskip.dto.UserDTO;
import com.brokerskip.entity.OTP;
import com.brokerskip.entity.User;
import com.brokerskip.exception.DuplicateResourceException;
import com.brokerskip.exception.InvalidOtpException;
import com.brokerskip.service.AuthService;
import com.brokerskip.service.OtpService;
import com.brokerskip.service.UserService;
import com.brokerskip.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public OtpResponse sendOtp(OtpRequest request) {
        log.info("Sending OTP to phone number: {}", request.getPhoneNumber());
        
        // Generate OTP
        OTP otp = otpService.generateAndSaveOtp(request.getPhoneNumber());
        
        // In production, send OTP via SMS
        // For now, logging the OTP
        log.debug("OTP generated: {} for phone: {}", otp.getOtpCode(), request.getPhoneNumber());
        
        OtpResponse response = new OtpResponse();
        response.setSuccess(true);
        response.setMessage("OTP sent successfully");
        
        return response;
    }

    @Override
    public OtpResponse verifyOtp(OtpVerifyRequest request) {
        log.info("Verifying OTP for phone number: {}", request.getPhoneNumber());
        
        // Verify OTP
        boolean isValid = otpService.verifyOtp(request.getPhoneNumber(), request.getOtpCode());
        if (!isValid) {
            throw new InvalidOtpException("Invalid or expired OTP");
        }
        
        // Create user if not exists
        User user = userService.getUserByPhone(request.getPhoneNumber()) != null ? 
                userService.getUserEntity(userService.getUserByPhone(request.getPhoneNumber()).getId()) : null;
        
        if (user == null) {
            user = new User();
            user.setPhoneNumber(request.getPhoneNumber());
            user.setIsVerified(true);
            // Save user
        }
        
        // Generate JWT token
        String token = jwtUtil.generateToken(request.getPhoneNumber());
        
        OtpResponse response = new OtpResponse();
        response.setSuccess(true);
        response.setMessage("OTP verified successfully");
        response.setToken(token);
        response.setUserId(user.getId());
        response.setProfileExists(user.getEmail() != null);
        
        log.info("OTP verified successfully for phone: {}", request.getPhoneNumber());
        
        return response;
    }

    @Override
    public void logout(String token) {
        log.info("User logged out");
        // Implement token blacklist if needed
    }
}
