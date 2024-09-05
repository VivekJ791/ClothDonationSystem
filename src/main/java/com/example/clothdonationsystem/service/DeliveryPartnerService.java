package com.example.clothdonationsystem.service;

import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.model.request.DeliveryPartnerRequest;

public interface DeliveryPartnerService {
    public Long createDeliveryPartner(DeliveryPartnerRequest request) throws ResourceNotFoundException;
}
