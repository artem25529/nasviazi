package com.es.nasviazi.repository;

import com.es.nasviazi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
