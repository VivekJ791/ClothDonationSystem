package com.example.clothdonationsystem.model.response;

import com.example.clothdonationsystem.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    Long id;
    Long userId;

    String paymentId;

    String paypalPayerId;

    BigDecimal amount;

    String paymentStatus;
}
