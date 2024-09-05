package com.example.clothdonationsystem.repo;

import com.example.clothdonationsystem.model.User;
import com.example.clothdonationsystem.model.response.UserResponse;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByMobileNo(String mobileNo);

    @Query("select u from User u where u.username like CONCAT('%', :username, '%')")
    User getUserByUsername(@Param("username") String username);

    User findUserByEmail(String email);

}
