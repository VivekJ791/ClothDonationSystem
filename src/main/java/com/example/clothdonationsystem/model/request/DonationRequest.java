package com.example.clothdonationsystem.model.request;

import com.example.clothdonationsystem.model.enums.DonationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonationRequest {
    Long userId;

    Long paymentId;

    Long totalItems;

    DonationStatus status;

}
