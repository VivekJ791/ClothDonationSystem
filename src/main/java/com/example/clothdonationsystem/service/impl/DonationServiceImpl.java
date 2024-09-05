package com.example.clothdonationsystem.service.impl;

import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.model.DeliveryPartner;
import com.example.clothdonationsystem.model.Donation;
import com.example.clothdonationsystem.model.User;
import com.example.clothdonationsystem.model.enums.DonationStatus;
import com.example.clothdonationsystem.model.request.DonationRequest;
import com.example.clothdonationsystem.repo.DeliveryPartnerRepository;
import com.example.clothdonationsystem.repo.DonationRepository;
import com.example.clothdonationsystem.repo.UserRepository;
import com.example.clothdonationsystem.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DonationServiceImpl implements DonationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeliveryPartnerRepository deliveryPartnerRepository;
    @Autowired
    DonationRepository donationRepository;


    public Long createDonation(DonationRequest request) throws ResourceNotFoundException {
        Donation donation= new Donation();
        User user= userRepository.findById(request.getUserId()).orElseThrow(()-> new ResourceNotFoundException("user for the given id doesnt exits"));
        donation.setUser(user!=null?user:null);
        donation.setDate(new Date());
        donation.setStatus(DonationStatus.PENDING);
        donation.setPaymentId(request.getPaymentId()!=null?request.getPaymentId():null);
        donation.setTotalItems(request.getTotalItems()!=null?request.getTotalItems():null);
        donationRepository.save(donation);

        return donation.getId();
    }

    @Override
    public void assignDeliveryPartner(Long deliveryPartnerId,Long donationId) throws ResourceNotFoundException {
        Donation donation=donationRepository.findById(donationId).orElseThrow(()-> new ResourceNotFoundException("no donation by the give id"));
        DeliveryPartner deliveryPartner= deliveryPartnerRepository.findById(deliveryPartnerId).orElseThrow(()-> new ResourceNotFoundException("no resource found for id: "+deliveryPartnerId));
        donation.setDeliveryPartner(deliveryPartner);
        deliveryPartner.setDonation(donation);
        donationRepository.save(donation);
        deliveryPartnerRepository.save(deliveryPartner);
    }

    @Override
    public void confirmDonation(Long donationId,Long paymentId) throws ResourceNotFoundException {
        Donation donation=donationRepository.findById(donationId).orElseThrow(()-> new ResourceNotFoundException("no donation by the give id"));
        donation.setStatus(DonationStatus.CONFIRMED);
        donation.setPaymentId(paymentId);
        donationRepository.save(donation);
    }


}
