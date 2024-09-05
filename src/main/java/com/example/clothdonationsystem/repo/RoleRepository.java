package com.example.clothdonationsystem.repo;

import com.example.clothdonationsystem.model.Role;
import com.example.clothdonationsystem.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleEnum name);

    @Query("SELECT r FROM Role r WHERE r.roleName = :name ")
    Optional<Role> findByRole(RoleEnum name);

}
