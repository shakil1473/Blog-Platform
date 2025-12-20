package com.shakil1473.dailydairy.service.implementations;

import com.shakil1473.dailydairy.domain.entity.Post;
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
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User removeUserPosts(UUID userId, Post post) {
        User existingUser = getUserById(userId);
        if(existingUser == null) {
            throw new EntityNotFoundException("User not found");
        }

        if(!existingUser.getPosts().contains(post)) {
            throw new EntityNotFoundException("Post with id " + post.getId() + " not found for the user");
        }

        existingUser.getPosts().remove(post);
        return userRepository.save(existingUser);
    }

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
