package com.brokerskip.util;

import java.util.Random;

public class OtpUtil {

    private static final Random random = new Random();

    /**
     * Generate a 6-digit OTP
     */
    public static String generateOtp() {
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    /**
     * Validate OTP format
     */
    public static boolean isValidOtp(String otp) {
        return otp != null && otp.matches("^[0-9]{6}$");
    }
}
