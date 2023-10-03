package com.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    /*
     * accepting username as paramater instead of email
     * spring security working with username
     * we use email instead of username
     */
    Optional<User> findByEmail(String username);
}
