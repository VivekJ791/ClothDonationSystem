package com.example.clothdonationsystem.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {
    @NotBlank
    Double total;
    @NotBlank
    String currency;
    String description;
    String cancelUrl;
    String successUr;

}
