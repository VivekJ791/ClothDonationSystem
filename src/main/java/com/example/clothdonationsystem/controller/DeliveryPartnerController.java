package com.example.clothdonationsystem.controller;

import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.model.request.DeliveryPartnerRequest;
import com.example.clothdonationsystem.model.response.MessageResponse;
import com.example.clothdonationsystem.service.DeliveryPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deliveryPartner")
public class DeliveryPartnerController {
    @Autowired
    DeliveryPartnerService deliveryPartnerService;

    @PostMapping("/createDeliveryPartner")
    public ResponseEntity<MessageResponse> createDeliveryPartner(@RequestBody DeliveryPartnerRequest request) throws ResourceNotFoundException {
        deliveryPartnerService.createDeliveryPartner(request);
        return ResponseEntity.ok(new MessageResponse("created successfully"));
    }

}
