package com.louiskhanh.airbnb_clone_be.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.louiskhanh.airbnb_clone_be.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findOneByEmail(String email);
    Optional<User> findOneByPublicId(UUID publicId);
}
