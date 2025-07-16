package com.louiskhanh.airbnb_clone_be.user.presentation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.louiskhanh.airbnb_clone_be.user.application.dto.ReadUserDTO;
import com.louiskhanh.airbnb_clone_be.user.domain.User;
import com.louiskhanh.airbnb_clone_be.user.mapper.UserMapper;
import com.louiskhanh.airbnb_clone_be.user.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class TestController {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    public TestController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    
    @GetMapping("/user-test")
    @Transactional(readOnly = true)
    public ResponseEntity<List<ReadUserDTO>> getAllUsers() {
        List<User> users = userRepository.findAll();
        System.out.println("Found " + users.size() + " users in database");
        
        List<ReadUserDTO> userDTOs = users.stream()
                .map(userMapper::readUserDTOToUser)
                .collect(Collectors.toList());
        
        System.out.println("Converted to " + userDTOs.size() + " DTOs");
        
        return ResponseEntity.ok(userDTOs);
    }
} 