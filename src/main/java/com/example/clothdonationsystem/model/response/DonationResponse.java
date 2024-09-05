package com.example.clothdonationsystem.model.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DonationResponse {
    Long donationId;
    Long userId;
    Long deliveryPartnerId;
    Long paymentId;
    String donationStatus;
    Date date;
    Long totalItem;
}
