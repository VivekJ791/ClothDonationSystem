package com.example.clothdonationsystem.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;

    String username;

    String email;

    String mobileNo;

    List<String> roles;

    String address;

    List<Long> donationIds;

    List<Long> paymentIds;

    List<Long> feedbackIds;
}
