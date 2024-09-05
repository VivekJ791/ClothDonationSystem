package com.example.clothdonationsystem.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressRequest {

    Long id;

    @NotBlank
    String street;

    @NotBlank
    String city;

    String state;

    @NotBlank
    String postalCode;
}
