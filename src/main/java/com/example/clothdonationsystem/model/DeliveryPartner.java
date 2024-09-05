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
public class DeliveryPartner {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    String name;

    String contactNumber;

    String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    Address address;

    @OneToOne(mappedBy = "deliveryPartner")
    Donation donation;
}
