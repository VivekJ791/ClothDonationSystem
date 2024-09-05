package com.example.clothdonationsystem.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @OneToOne(mappedBy = "address")
    User user;

    @OneToOne(mappedBy = "address")
    DeliveryPartner deliveryPartner;

    String street;

    String city;

    String state;

    String postalCode;
}
