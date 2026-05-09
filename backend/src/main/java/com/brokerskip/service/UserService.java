package com.brokerskip.service;

import com.brokerskip.dto.UserDTO;
import com.brokerskip.dto.UserProfileRequest;
import com.brokerskip.entity.User;

public interface UserService {

    UserDTO createUserProfile(Integer userId, UserProfileRequest request);

    UserDTO getUserProfile(Integer userId);

    UserDTO updateUserProfile(Integer userId, UserProfileRequest request);

    User getUserEntity(Integer userId);

    boolean userExists(Integer userId);

    UserDTO getUserByPhone(String phoneNumber);
}
