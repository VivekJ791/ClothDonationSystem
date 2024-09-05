package com.example.clothdonationsystem.model;

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
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // Many-to-One relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;  // Using User entity directly

    String comment;

    Integer rating;  // Assuming a rating scale, e.g., 1-5

    @Column(name = "feedback_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date date;

}