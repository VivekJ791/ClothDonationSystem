package com.example.clothdonationsystem.model;

import com.example.clothdonationsystem.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String paymentId;

    String paypalPayerId;

    BigDecimal amount= BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;

}
