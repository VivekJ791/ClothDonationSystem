package com.example.clothdonationsystem.service;

import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.model.request.AddressRequest;

public interface AddressService {
    public Long saveAddress(Long  userId, AddressRequest addressRequest) throws ResourceNotFoundException;
    public Long saveAddress(AddressRequest addressRequest) throws ResourceNotFoundException;
}
