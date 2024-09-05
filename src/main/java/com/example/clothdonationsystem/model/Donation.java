package com.example.clothdonationsystem.model;

import com.example.clothdonationsystem.model.enums.DonationStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Donation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_partner_id")
    DeliveryPartner deliveryPartner;

    @Temporal(TemporalType.TIMESTAMP)
    Date date;

    @Enumerated(EnumType.STRING)
    DonationStatus status;

    Long paymentId;

    Long totalItems;
}
