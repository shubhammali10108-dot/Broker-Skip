package com.brokerskip.service.impl;

import com.brokerskip.entity.OTP;
import com.brokerskip.entity.User;
import com.brokerskip.exception.InvalidOtpException;
import com.brokerskip.repository.OtpRepository;
import com.brokerskip.repository.UserRepository;
import com.brokerskip.service.OtpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${otp.expiration.minutes:5}")
    private Integer otpExpirationMinutes;

    @Override
    public OTP generateAndSaveOtp(String phoneNumber) {
        log.info("Generating OTP for phone: {}", phoneNumber);
        
        // Create user if doesn't exist
        Optional<User> existingUser = userRepository.findByPhoneNumber(phoneNumber);
        if (existingUser.isEmpty()) {
            User newUser = new User();
            newUser.setPhoneNumber(phoneNumber);
            newUser.setIsVerified(false);
            userRepository.save(newUser);
            log.info("New user created for phone: {}", phoneNumber);
        }
        
        // Generate random 6-digit OTP
        String otpCode = String.format("%06d", new Random().nextInt(999999));
        
        OTP otp = new OTP();
        otp.setPhoneNumber(phoneNumber);
        otp.setOtpCode(otpCode);
        otp.setIsVerified(false);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(otpExpirationMinutes));
        
        OTP savedOtp = otpRepository.save(otp);
        log.info("OTP saved successfully for phone: {} with expiry: {}", phoneNumber, otp.getExpiresAt());
        
        return savedOtp;
    }

    @Override
    public OTP getLatestOtp(String phoneNumber) {
        log.info("Fetching latest OTP for phone: {}", phoneNumber);
        
        return otpRepository.findTopByPhoneNumberOrderByCreatedAtDesc(phoneNumber)
                .orElseThrow(() -> new InvalidOtpException("No OTP found for this phone number"));
    }

    @Override
    public boolean verifyOtp(String phoneNumber, String otpCode) {
        log.info("Verifying OTP for phone: {}", phoneNumber);
        
        OTP otp = otpRepository.findByPhoneNumberAndOtpCode(phoneNumber, otpCode)
                .orElseThrow(() -> new InvalidOtpException("Invalid OTP"));
        
        if (isOtpExpired(otp)) {
            throw new InvalidOtpException("OTP has expired");
        }
        
        otp.setIsVerified(true);
        otpRepository.save(otp);
        
        // Mark user as verified
        Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);
        if (user.isPresent()) {
            user.get().setIsVerified(true);
            userRepository.save(user.get());
        }
        
        log.info("OTP verified successfully for phone: {}", phoneNumber);
        return true;
    }

    @Override
    public void deleteOtp(Integer otpId) {
        log.info("Deleting OTP with id: {}", otpId);
        otpRepository.deleteById(otpId);
    }

    @Override
    public boolean isOtpExpired(OTP otp) {
        return LocalDateTime.now().isAfter(otp.getExpiresAt());
    }
}
