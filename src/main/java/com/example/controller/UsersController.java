package com.example.controller;

import com.example.service.UserService;
import com.example.dto.UserRegisterRequest;
import com.example.dto.UserLoginRequest;
import com.example.dto.UserProfileUpdateRequest;
import com.example.dto.PasswordUpdateRequest;
import com.example.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        logger.info("Registering new user with username: {}", request.getUsername());
        ApiResponse response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@Valid @RequestBody UserLoginRequest request) {
        logger.info("Login attempt for username: {}", request.getUsername());
        ApiResponse response = userService.loginUser(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse> getUserProfile(@RequestHeader("Authorization") String token) {
        logger.info("Fetching user profile");
        ApiResponse response = userService.getUserProfile(token);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> updateUserProfile(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody UserProfileUpdateRequest request) {
        logger.info("Updating user profile");
        ApiResponse response = userService.updateUserProfile(token, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/password")
    public ResponseEntity<ApiResponse> updatePassword(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody PasswordUpdateRequest request) {
        logger.info("Updating user password");
        ApiResponse response = userService.updatePassword(token, request);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        logger.error("An error occurred: ", e);
        ApiResponse response = new ApiResponse(false, e.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }
}