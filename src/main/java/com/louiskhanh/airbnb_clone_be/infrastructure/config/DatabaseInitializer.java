package com.louiskhanh.airbnb_clone_be.infrastructure.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.louiskhanh.airbnb_clone_be.user.domain.Authority;
import com.louiskhanh.airbnb_clone_be.user.repository.AuthorityRepository;

@Configuration
public class DatabaseInitializer {

    @Bean
    @Transactional
    public CommandLineRunner initDatabase(AuthorityRepository authorityRepository) {
        return args -> {
            // Define all required authorities
            String[] authorityNames = {
                SecurityUtils.ROLE_TENANT,
                SecurityUtils.ROLE_ADMIN,
                "ROLE_LANDLORD"  // Add this role since it appears in Auth0
            };

            // Check and create missing authorities
            Arrays.stream(authorityNames).forEach(name -> {
                if (!authorityRepository.existsById(name)) {
                    Authority authority = new Authority();
                    authority.setName(name);
                    authorityRepository.save(authority);
                    System.out.println("Created authority: " + name);
                }
            });
        };
    }
} 