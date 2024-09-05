package com.example.clothdonationsystem.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpRequest {
    @NotBlank
    String email;

    @NotBlank
    String username;

    @NotBlank
    String mobileNo;

    Set<String> role;

    @NotBlank
    String password;

    AddressRequest addressRequest;

}
