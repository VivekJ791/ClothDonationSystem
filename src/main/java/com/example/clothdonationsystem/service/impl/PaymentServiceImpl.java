package com.example.clothdonationsystem.service.impl;

import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.model.Payment;
import com.example.clothdonationsystem.model.User;
import com.example.clothdonationsystem.model.enums.PaymentStatus;
import com.example.clothdonationsystem.model.request.PaymentRequest;
import com.example.clothdonationsystem.repo.PaymentRepository;
import com.example.clothdonationsystem.repo.UserRepository;
import com.example.clothdonationsystem.service.PaymentService;
import com.example.clothdonationsystem.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentRepository paymentRepository;


    @Override
    public Long createPayment(PaymentRequest request) throws ResourceNotFoundException {
        Payment payment= new Payment();
        User user= userRepository.getUserByUsername(UserUtils.getCurrentUsername());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setAmount(BigDecimal.valueOf(request.getTotal()));
        payment.setUser(user);

        paymentRepository.save(payment);

        Set<Payment> payments = user.getPayments();
        payments.add(payment);
        userRepository.save(user);

        return payment.getId();
    }

    @Override
    public Long updatePayment(String paymentId,String payerId,Long id) throws ResourceNotFoundException {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("no such payment exists"));
        payment.setPaymentId(paymentId);
        payment.setPaypalPayerId(payerId);
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);
        return payment.getId();
    }

}
