package com.brokerskip.service.impl;

import com.brokerskip.dto.UserDTO;
import com.brokerskip.dto.UserProfileRequest;
import com.brokerskip.entity.User;
import com.brokerskip.exception.DuplicateResourceException;
import com.brokerskip.exception.ResourceNotFoundException;
import com.brokerskip.repository.UserRepository;
import com.brokerskip.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO createUserProfile(Integer userId, UserProfileRequest request) {
        log.info("Creating user profile for userId: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail()) && !user.getEmail().equals(request.getEmail())) {
            throw new DuplicateResourceException("User", "email", request.getEmail());
        }
        
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setProfilePhotoUrl(request.getProfilePhotoUrl());
        user.setBio(request.getBio());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setPincode(request.getPincode());
        user.setIsVerified(true);
        
        User savedUser = userRepository.save(user);
        log.info("User profile created successfully for userId: {}", userId);
        
        return mapToDTO(savedUser);
    }

    @Override
    public UserDTO getUserProfile(Integer userId) {
        log.info("Fetching user profile for userId: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        return mapToDTO(user);
    }

    @Override
    public UserDTO updateUserProfile(Integer userId, UserProfileRequest request) {
        log.info("Updating user profile for userId: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getProfilePhotoUrl() != null) user.setProfilePhotoUrl(request.getProfilePhotoUrl());
        if (request.getBio() != null) user.setBio(request.getBio());
        if (request.getCity() != null) user.setCity(request.getCity());
        if (request.getState() != null) user.setState(request.getState());
        if (request.getPincode() != null) user.setPincode(request.getPincode());
        
        User updatedUser = userRepository.save(user);
        log.info("User profile updated successfully for userId: {}", userId);
        
        return mapToDTO(updatedUser);
    }

    @Override
    public User getUserEntity(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    @Override
    public boolean userExists(Integer userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public UserDTO getUserByPhone(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .map(this::mapToDTO)
                .orElse(null);
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profilePhotoUrl(user.getProfilePhotoUrl())
                .bio(user.getBio())
                .city(user.getCity())
                .state(user.getState())
                .pincode(user.getPincode())
                .isVerified(user.getIsVerified())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
