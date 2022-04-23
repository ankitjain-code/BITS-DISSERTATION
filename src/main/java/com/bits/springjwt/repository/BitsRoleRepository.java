package com.bits.springjwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bits.springjwt.models.BitsEnumRole;
import com.bits.springjwt.models.BitsRole;

@Repository
public interface BitsRoleRepository extends JpaRepository<BitsRole, Long> {
  Optional<BitsRole> findByName(BitsEnumRole name);
}
