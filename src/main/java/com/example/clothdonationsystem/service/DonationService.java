package com.example.clothdonationsystem.service;

import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.model.request.DonationRequest;

public interface DonationService {
    public Long createDonation(DonationRequest request) throws ResourceNotFoundException;

    void assignDeliveryPartner(Long deliveryPartnerId,Long donationId) throws ResourceNotFoundException;

    void confirmDonation(Long donationId,Long paymentId) throws ResourceNotFoundException;
}
