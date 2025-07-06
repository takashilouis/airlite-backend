package com.louiskhanh.airbnb_clone_be.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.louiskhanh.airbnb_clone_be.user.domain.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
    // Basic CRUD operations are provided by JpaRepository
} 