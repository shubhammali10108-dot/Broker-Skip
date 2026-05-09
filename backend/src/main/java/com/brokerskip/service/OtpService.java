package com.brokerskip.service;

import com.brokerskip.entity.OTP;

public interface OtpService {

    OTP generateAndSaveOtp(String phoneNumber);

    OTP getLatestOtp(String phoneNumber);

    boolean verifyOtp(String phoneNumber, String otpCode);

    void deleteOtp(Integer otpId);

    boolean isOtpExpired(OTP otp);
}
