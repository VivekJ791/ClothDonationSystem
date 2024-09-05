package com.example.clothdonationsystem.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OTPService {

    private static final int OTP_LENGTH = 6; // Length of OTP
    private static final int OTP_EXPIRATION_TIME = 5 * 60 * 1000; // 5 minutes in milliseconds

    private final Map<String, OTPDetails> otpStorage = new HashMap<>();

    // Method to generate OTP
    public String generateOTP(String key) {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10)); // Generate a random digit
        }

        // Store OTP with expiration
        OTPDetails otpDetails = new OTPDetails(otp.toString(), System.currentTimeMillis() + OTP_EXPIRATION_TIME);
        otpStorage.put(key, otpDetails);

        return otp.toString();
    }

    // Method to validate OTP
    public boolean validateOTP(String key, String otp) {
        OTPDetails otpDetails = otpStorage.get(key);
        if (otpDetails != null && otpDetails.getOtp().equals(otp) && otpDetails.getExpiryTime() > System.currentTimeMillis()) {
            otpStorage.remove(key); // Remove OTP after successful validation
            return true;
        }
        return false;
    }

    private static class OTPDetails {
        private final String otp;
        private final long expiryTime;

        public OTPDetails(String otp, long expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        public String getOtp() {
            return otp;
        }

        public long getExpiryTime() {
            return expiryTime;
        }
    }
}
