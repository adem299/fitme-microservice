package com.fitme.userservice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitme.userservice.dto.RegisterRequest;
import com.fitme.userservice.dto.UserResponse;
import com.fitme.userservice.exception.EmailAlreadyExistsException;
import com.fitme.userservice.exception.UserNotFoundException;
import com.fitme.userservice.mapper.UserMapper;
import com.fitme.userservice.model.User;
import com.fitme.userservice.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse getUserProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        
        return userMapper.toUserResponse(user);
    }

    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("User already exists");
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.password()));

        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    public Boolean existByUserId(String userId) {
        return userRepository.existsById(userId);
    }
}
