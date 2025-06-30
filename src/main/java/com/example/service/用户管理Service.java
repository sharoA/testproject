package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
            logger.info("User created successfully: {}", savedUser.getId());
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
            if (user.isPresent()) {
                logger.info("Retrieved user with ID: {}", id);
                return user.get();
            } else {
                throw new IllegalArgumentException("User not found with ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Error retrieving user with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            logger.info("Retrieved all users, count: {}", users.size());
            return users;
        } catch (Exception e) {
            logger.error("Error retrieving all users: {}", e.getMessage());
            throw e;
        }
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

            if (!user.getUsername().equals(userDetails.getUsername()) && 
                userRepository.existsByUsername(userDetails.getUsername())) {
                throw new IllegalArgumentException("Username already exists");
            }

            if (!user.getEmail().equals(userDetails.getEmail()) && 
                userRepository.existsByEmail(userDetails.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }

            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setEmail(userDetails.getEmail());
            user.setPhone(userDetails.getPhone());
            user.setUpdatedAt(LocalDateTime.now());

            User updatedUser = userRepository.save(user);
            logger.info("User updated successfully: {}", id);
            return updatedUser;
        } catch (Exception e) {
            logger.error("Error updating user with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void deleteUser(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));

            userRepository.delete(user);
            logger.info("User deleted successfully: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public User login(String username, String password) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

            if (!user.getPassword().equals(password)) {
                throw new IllegalArgumentException("Invalid username or password");
            }

            logger.info("User logged in successfully: {}", username);
            return user;
        } catch (Exception e) {
            logger.error("Login failed for username {}: {}", username, e.getMessage());
            throw e;
        }
    }
}