package com.example.clothdonationsystem.service.impl;

import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.model.Address;
import com.example.clothdonationsystem.model.User;
import com.example.clothdonationsystem.model.request.AddressRequest;
import com.example.clothdonationsystem.model.request.SignUpRequest;
import com.example.clothdonationsystem.repo.AddressRepository;
import com.example.clothdonationsystem.repo.UserRepository;
import com.example.clothdonationsystem.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired AddressRepository addressRepository;
    @Autowired UserRepository userRepository;
    @Override
    public Long saveAddress(Long  userId,AddressRequest addressRequest) throws ResourceNotFoundException {
        //create address
        Address address=new Address();
        User user=null;
        if(addressRequest.getId()!=null){
            address = addressRepository.findById(addressRequest.getId()).orElseThrow(() -> new ResourceNotFoundException("address not present for this id"));

        }
//            if(userId!=null){
//                user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("no user for such id"));
//            }
            address.setCity(addressRequest.getCity() != null ? addressRequest.getCity() : address.getCity());
            address.setState(addressRequest.getState() != null ? addressRequest.getState() : address.getState());
            address.setPostalCode(addressRequest.getPostalCode() != null ? addressRequest.getPostalCode() : address.getPostalCode());
            address.setStreet(addressRequest.getStreet() != null ? addressRequest.getStreet() : address.getStreet());
            address.setUser(user!=null?user:null);
        addressRepository.save(address);
        return address.getId();
    }

    @Override
    public Long saveAddress(AddressRequest addressRequest) throws ResourceNotFoundException {
        //create address
        Address address=new Address();
        if(addressRequest.getId()!=null){
            address = addressRepository.findById(addressRequest.getId()).orElseThrow(() -> new ResourceNotFoundException("address not present for this id"));
        }else{
            address.setCity(addressRequest.getCity() != null ? addressRequest.getCity() : address.getCity());
            address.setState(addressRequest.getState() != null ? addressRequest.getState() : address.getState());
            address.setPostalCode(addressRequest.getPostalCode() != null ? addressRequest.getPostalCode() : address.getPostalCode());
            address.setStreet(addressRequest.getStreet() != null ? addressRequest.getStreet() : address.getStreet());
            address.setDeliveryPartner(null);
        }
        addressRepository.save(address);
        return address.getId();
    }
}
