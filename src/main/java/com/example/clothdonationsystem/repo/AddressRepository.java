package com.example.clothdonationsystem.repo;

import com.example.clothdonationsystem.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
