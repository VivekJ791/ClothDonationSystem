package com.example.clothdonationsystem.repo;

import com.example.clothdonationsystem.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation,Long> {

}
