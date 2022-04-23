package com.bits.springjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bits.springjwt.models.BitsUser;

@Repository
public interface BitsUserRepository extends JpaRepository<BitsUser, Long> {
  Optional<BitsUser> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
