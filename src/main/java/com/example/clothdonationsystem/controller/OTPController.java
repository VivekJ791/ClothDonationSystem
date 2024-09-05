package com.example.clothdonationsystem.controller;

import com.example.clothdonationsystem.service.EmailService;
import com.example.clothdonationsystem.service.OTPService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/otp")
public class OTPController {

    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/generate")
    public String generateOTP(@RequestParam String email) throws MessagingException, UnsupportedEncodingException {
        String otp = otpService.generateOTP(email);
        emailService.sendOTPEmail(email, otp);
        return "OTP sent to your email address!";
    }

    @PostMapping("/validate")
    public String validateOTP(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = otpService.validateOTP(email, otp);
        if (isValid) {
            return "OTP is valid!";
        } else {
            return "Invalid OTP or OTP expired!";
        }
    }
}
