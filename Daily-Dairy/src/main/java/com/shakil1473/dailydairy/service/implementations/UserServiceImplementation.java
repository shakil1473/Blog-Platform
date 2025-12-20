package com.shakil1473.dailydairy.service.implementations;

import com.shakil1473.dailydairy.domain.entity.User;
import com.shakil1473.dailydairy.repository.UserRepository;
import com.shakil1473.dailydairy.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(UUID userId) {
        return userRepository
                .findById(userId)
                .orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElse(null);
    }

    @Override
    public User getUserByIdAndEmail(UUID userId, String email) {
        return userRepository
                .findByIdAndEmail(userId, email)
                .orElse(null);
    }


}
