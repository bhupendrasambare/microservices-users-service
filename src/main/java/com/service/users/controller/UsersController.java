/**
 * author @bhupendrasambare
 * Date   :03/09/24
 * Time   :11:37 pm
 * Project:user service
 **/
package com.service.users.controller;

import com.service.users.dto.request.UserUpdateRequest;
import com.service.users.dto.response.Response;
import com.service.users.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "APIs for managing user profiles")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Operation(summary = "Get user profile by ID", description = "Fetches user profile information based on the provided user ID.")
    @GetMapping("/profile")
    public ResponseEntity<Response> getUserProfile() {
        return usersService.getUserById();
    }

    @Operation(summary = "Update user profile", description = "Updates user profile details such as first name, last name, phone number, profile picture, and email.")
    @PutMapping("/profile")
    public ResponseEntity<Response> updateUserProfile(@ModelAttribute UserUpdateRequest request) {
        return usersService.updateUser(request);
    }
}