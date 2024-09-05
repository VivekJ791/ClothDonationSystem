package com.example.clothdonationsystem.repo;

import com.example.clothdonationsystem.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
