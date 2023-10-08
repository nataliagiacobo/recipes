package com.ada.recipes.repository;

import com.ada.recipes.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    Page<User> findAll(Pageable pageable);
    UserDetails findByEmail(String email);
    List<User> findAllByName(String name);
}