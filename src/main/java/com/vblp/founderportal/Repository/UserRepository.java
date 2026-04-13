package com.vblp.founderportal.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vblp.founderportal.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameIgnoreCase(String username);
}