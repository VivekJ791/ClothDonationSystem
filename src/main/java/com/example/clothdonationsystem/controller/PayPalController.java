package com.example.clothdonationsystem.controller;

import com.example.clothdonationsystem.model.request.PaymentRequest;
import com.example.clothdonationsystem.service.DonationService;
import com.example.clothdonationsystem.service.PayPalService;
import com.example.clothdonationsystem.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/paypal")
public class PayPalController {

    @Autowired
    private PayPalService payPalService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    DonationService donationService;

    Long id;

    // Endpoint to execute a payment after approval
    @PostMapping("/start-payment")
    public ResponseEntity<Map<String, Object>> startPayment(
            @RequestBody PaymentRequest request) {

        try {
            Map<String, Object> paymentResponse = payPalService.createPayment(request.getTotal(), request.getCurrency(), request.getDescription(), request.getCancelUrl(), request.getSuccessUr());
            String approvalUrl = (String) paymentResponse.get("approvalUrl");

            if (approvalUrl != null) {
                //save user plus payment details status pending
                id = paymentService.createPayment(request);
                return ResponseEntity.ok(paymentResponse); // Return payment details, including approval URL
            } else {
                return ResponseEntity.badRequest().body(Map.of("error", "Unable to get approval URL"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/execute-payment")
    public ResponseEntity<Map<String, Object>> executePayment(
            @RequestParam String paymentId,
            @RequestParam String payerId,
            @RequestParam Long donationId) {

        try {
            Map<String, Object> executedPayment = payPalService.executePayment(paymentId, payerId);
            //update status completed,payment id , paypal id

            paymentService.updatePayment(paymentId, payerId, id);
            donationService.confirmDonation(donationId,id);
            return ResponseEntity.ok(executedPayment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //creata an cancel one make status failed
}

