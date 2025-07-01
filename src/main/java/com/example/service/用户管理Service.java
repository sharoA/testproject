package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserManagementService {

    private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        try {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new IllegalArgumentException("Username already exists");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }

            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            User savedUser = userRepository.save(user);
            logger.info("User created successfully with ID: {}", savedUser.getId());
            return savedUser;
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isEmpty()) {
                throw new IllegalArgumentException("User not found with ID: " + id);
            }
            return user.get();
        } catch (Exception e) {
            logger.error("Error retrieving user with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public User login(String username, String password) {
        try {
            Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
            if (user.isEmpty()) {
                throw new IllegalArgumentException("Invalid username or password");
            }
            logger.info("User {} logged in successfully", username);
            return user.get();
        } catch (Exception e) {
            logger.error("Login failed for username {}: {}", username, e.getMessage());
            throw e;
        }
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

            if (userDetails.getUsername() != null) {
                if (!user.getUsername().equals(userDetails.getUsername()) && 
                    userRepository.existsByUsername(userDetails.getUsername())) {
                    throw new IllegalArgumentException("Username already exists");
                }
                user.setUsername(userDetails.getUsername());
            }

            if (userDetails.getPassword() != null) {
                user.setPassword(userDetails.getPassword());
            }

            if (userDetails.getEmail() != null) {
                if (!user.getEmail().equals(userDetails.getEmail()) && 
                    userRepository.existsByEmail(userDetails.getEmail())) {
                    throw new IllegalArgumentException("Email already exists");
                }
                user.setEmail(userDetails.getEmail());
            }

            if (userDetails.getPhone() != null) {
                user.setPhone(userDetails.getPhone());
            }

            user.setUpdatedAt(LocalDateTime.now());
            User updatedUser = userRepository.save(user);
            logger.info("User with ID {} updated successfully", id);
            return updatedUser;
        } catch (Exception e) {
            logger.error("Error updating user with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                throw new IllegalArgumentException("User not found with ID: " + id);
            }
            userRepository.deleteById(id);
            logger.info("User with ID {} deleted successfully", id);
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }
}