package com.es.nasviazi.repository;

import com.es.nasviazi.model.Authority;
import com.es.nasviazi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthority(String authority);
}
