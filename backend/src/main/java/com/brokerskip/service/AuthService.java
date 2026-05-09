package com.brokerskip.service;

import com.brokerskip.dto.OtpRequest;
import com.brokerskip.dto.OtpResponse;
import com.brokerskip.dto.OtpVerifyRequest;

public interface AuthService {

    OtpResponse sendOtp(OtpRequest request);

    OtpResponse verifyOtp(OtpVerifyRequest request);

    void logout(String token);
}
