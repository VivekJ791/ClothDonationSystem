package com.example.clothdonationsystem.service.impl;

import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.model.Address;
import com.example.clothdonationsystem.model.DeliveryPartner;
import com.example.clothdonationsystem.model.request.DeliveryPartnerRequest;
import com.example.clothdonationsystem.repo.AddressRepository;
import com.example.clothdonationsystem.repo.DeliveryPartnerRepository;
import com.example.clothdonationsystem.service.AddressService;
import com.example.clothdonationsystem.service.DeliveryPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    AddressService addressService;
    @Autowired
    DeliveryPartnerRepository deliveryPartnerRepository;

    @Override
    public Long createDeliveryPartner(DeliveryPartnerRequest request) throws ResourceNotFoundException {
        DeliveryPartner deliveryPartner= new DeliveryPartner();
        if(request.getId()!=null){
            deliveryPartner= deliveryPartnerRepository.findById(request.getId()).orElseThrow(()-> new ResourceNotFoundException("no delivery partner for the given id"));
        }
        deliveryPartner.setName(request.getName()!=null? request.getName() : null);
        deliveryPartner.setEmail(request.getEmail()!=null? request.getEmail(): null);
        deliveryPartner.setContactNumber(request.getContactNumber()!=null? request.getContactNumber() : null);

        Address address= new Address();
        Long addressId = addressService.saveAddress(request.getAddressRequest());
        address= addressRepository.findById(addressId).orElseThrow(()-> new ResourceNotFoundException("no address for this id"));
        deliveryPartner.setAddress(address);

        deliveryPartnerRepository.save(deliveryPartner);

        address.setDeliveryPartner(deliveryPartner);
        addressRepository.save(address);

        return deliveryPartner.getId();
    }
}
