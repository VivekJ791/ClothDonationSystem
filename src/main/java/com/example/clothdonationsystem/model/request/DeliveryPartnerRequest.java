package com.example.clothdonationsystem.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryPartnerRequest {

    Long id;

    @NotBlank
    String name;

    @NotBlank
    String contactNumber;

    @NotBlank
    String email;

    AddressRequest addressRequest;
}
