package com.louiskhanh.airbnb_clone_be.user.application;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.louiskhanh.airbnb_clone_be.infrastructure.config.SecurityUtils;
import com.louiskhanh.airbnb_clone_be.user.application.dto.ReadUserDTO;
import com.louiskhanh.airbnb_clone_be.user.domain.User;
import com.louiskhanh.airbnb_clone_be.user.mapper.UserMapper;
import com.louiskhanh.airbnb_clone_be.user.repository.UserRepository;
@Service
public class UserService {
    private static final String UPDATED_AT_KEY = "updated_at";
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    } 
    
    @Transactional(readOnly = true)
    public ReadUserDTO getAuthenticatedUserFromSecurityContext() {
        OAuth2User principal = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = SecurityUtils.mapOauth2AttributesToUser(principal.getAttributes());
        return getByEmail(user.getEmail()).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Optional<ReadUserDTO> getByEmail(String email) {
        Optional<User> oneByEmail = userRepository.findOneByEmail(email);
        return oneByEmail.map(userMapper::readUserDTOToUser);
    }

    @Transactional
    public void syncWithIdp(OAuth2User oAuth2User, boolean forceResync) {
        try {
            Map<String, Object> attributes = oAuth2User.getAttributes();
            User user = SecurityUtils.mapOauth2AttributesToUser(attributes);
            Optional<User> existingUser = userRepository.findOneByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                if (attributes.get(UPDATED_AT_KEY) != null) {
                    Instant lastModifiedDate = existingUser.orElseThrow().getLastModifiedDate();
                    Instant idpModifiedDate;
                    if (attributes.get(UPDATED_AT_KEY) instanceof Instant instant) {
                        idpModifiedDate = instant;
                    } else {
                        idpModifiedDate = Instant.ofEpochSecond((Integer) attributes.get(UPDATED_AT_KEY));
                    }
                    if (idpModifiedDate.isAfter(lastModifiedDate) || forceResync) {
                        updateUser(user);
                    }
                } else {
                    // If no updated_at timestamp, update anyway if forced
                    if (forceResync) {
                        updateUser(user);
                    }
                }
            } else {
                // New user, save it
                userRepository.saveAndFlush(user);
            }
        } catch (Exception e) {
            // Log the error
            System.err.println("Error in syncWithIdp: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw to let the controller handle it
        }
    }

    private void updateUser(User user) {
        Optional<User> userToUpdateOpt = userRepository.findOneByEmail(user.getEmail());
        if (userToUpdateOpt.isPresent()) {
            User userToUpdate = userToUpdateOpt.get();
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setAuthorities(user.getAuthorities());
            userToUpdate.setImageUrl(user.getImageUrl());
            // Don't update the publicId since it's already set and should be preserved
            userRepository.saveAndFlush(userToUpdate);
        }
    }

    public Optional<ReadUserDTO> getByPublicId(UUID publicId) {
        Optional<User> oneByPublicId = userRepository.findOneByPublicId(publicId);
        return oneByPublicId.map(userMapper::readUserDTOToUser);
    }
}
