package com.example.clothdonationsystem.controller;

import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.model.request.DonationRequest;
import com.example.clothdonationsystem.model.response.MessageResponse;
import com.example.clothdonationsystem.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    DonationService donationService;

    @PostMapping("/createDonation")
    public ResponseEntity<MessageResponse> createDonation(@RequestBody DonationRequest request) throws ResourceNotFoundException {
        donationService.createDonation(request);
        return ResponseEntity.ok(new MessageResponse("created successfully"));
    }

    @PutMapping("/confirmDonation")
    public ResponseEntity<MessageResponse> confirmDonation(@RequestParam Long donationId,Long paymentId) throws ResourceNotFoundException {
        donationService.confirmDonation(donationId,paymentId);
        return ResponseEntity.ok(new MessageResponse("confirmed successfully"));
    }

    @PutMapping("/assignDeliveryPartner")
    public ResponseEntity<MessageResponse> assignDeliveryPartner(@RequestParam Long deliveryPartnerId,@RequestParam Long donationId) throws ResourceNotFoundException {
        donationService.assignDeliveryPartner(deliveryPartnerId,donationId);
        return ResponseEntity.ok(new MessageResponse("delivery partner assigned successfully"));
    }

}
