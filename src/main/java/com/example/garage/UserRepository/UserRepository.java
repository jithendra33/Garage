package com.example.garage.UserRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.garage.UserEntity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAddress(String emailAddress);
    Optional<User> findByMobileNumber(String mobileNumber);
    Optional<User> findByMobileNumberAndPassword(String mobileNumber, String password);
}
