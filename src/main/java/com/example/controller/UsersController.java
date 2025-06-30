package com.example.controller;

import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/users")
@Validated
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        logger.info("Registering new user with username: {}", request.getUsername());
        try {
            UserResponse response = userService.registerUser(request);
            return ResponseEntity.ok(new ApiResponse<>("success", "User registered successfully", response));
        } catch (Exception e) {
            logger.error("Registration failed for username: {}", request.getUsername(), e);
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest request) {
        logger.info("Login attempt for username: {}", request.getUsername());
        try {
            AuthResponse response = userService.authenticateUser(request);
            return ResponseEntity.ok(new ApiResponse<>("success", "Login successful", response));
        } catch (Exception e) {
            logger.error("Login failed for username: {}", request.getUsername(), e);
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestHeader @NotBlank String authorization) {
        logger.info("Fetching user profile");
        try {
            UserProfileResponse response = userService.getUserProfile(authorization);
            return ResponseEntity.ok(new ApiResponse<>("success", "User profile retrieved", response));
        } catch (Exception e) {
            logger.error("Failed to fetch user profile", e);
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestHeader @NotBlank String authorization,
                                               @Valid @RequestBody UserProfileUpdateRequest request) {
        logger.info("Updating user profile");
        try {
            UserProfileResponse response = userService.updateUserProfile(authorization, request);
            return ResponseEntity.ok(new ApiResponse<>("success", "User profile updated", response));
        } catch (Exception e) {
            logger.error("Failed to update user profile", e);
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@RequestHeader @NotBlank String authorization,
                                           @Valid @RequestBody PasswordChangeRequest request) {
        logger.info("Changing user password");
        try {
            userService.changePassword(authorization, request);
            return ResponseEntity.ok(new ApiResponse<>("success", "Password changed successfully", null));
        } catch (Exception e) {
            logger.error("Failed to change password", e);
            return ResponseEntity.badRequest().body(new ApiResponse<>("error", e.getMessage(), null));
        }
    }

    // Inner classes for request/response objects
    static class UserRegistrationRequest {
        @NotBlank private String username;
        @NotBlank private String password;
        @NotBlank private String email;

        // Getters and Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    static class LoginRequest {
        @NotBlank private String username;
        @NotBlank private String password;

        // Getters and Setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    static class UserProfileUpdateRequest {
        private String displayName;
        private String email;
        private String avatarUrl;

        // Getters and Setters
        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getAvatarUrl() { return avatarUrl; }
        public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    }

    static class PasswordChangeRequest {
        @NotBlank private String oldPassword;
        @NotBlank private String newPassword;

        // Getters and Setters
        public String getOldPassword() { return oldPassword; }
        public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }

    static class ApiResponse<T> {
        private String status;
        private String message;
        private T data;

        public ApiResponse(String status, String message, T data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        // Getters
        public String getStatus() { return status; }
        public String getMessage() { return message; }
        public T getData() { return data; }
    }

    static class UserResponse {
        private String userId;
        private String username;
        private String email;

        // Getters and Setters
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    static class AuthResponse {
        private String token;
        private long expiresIn;

        // Getters and Setters
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public long getExpiresIn() { return expiresIn; }
        public void setExpiresIn(long expiresIn) { this.expiresIn = expiresIn; }
    }

    static class UserProfileResponse {
        private String userId;
        private String username;
        private String displayName;
        private String email;
        private String avatarUrl;

        // Getters and Setters
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getAvatarUrl() { return avatarUrl; }
        public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    }
}