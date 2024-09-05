package com.example.clothdonationsystem.service;

import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.model.request.PaymentRequest;

public interface PaymentService {
    public Long createPayment(PaymentRequest request) throws ResourceNotFoundException;

    public Long updatePayment(String paymentId,String payerId,Long id) throws ResourceNotFoundException;

}
