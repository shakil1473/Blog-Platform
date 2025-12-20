package com.shakil1473.dailydairy.service;

import com.shakil1473.dailydairy.domain.entity.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID userId);
    User getUserByEmail(String email);
    User getUserByIdAndEmail(UUID userId, String email);
}
