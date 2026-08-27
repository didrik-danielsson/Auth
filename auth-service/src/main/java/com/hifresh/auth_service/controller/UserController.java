package com.hifresh.auth_service.controller;

import com.hifresh.auth_service.models.dtos.UserDTO;
import com.hifresh.auth_service.models.enitities.User;
import com.hifresh.auth_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get the current user")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> authenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        assert authentication != null;
        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser.toDto());
    }
    @Operation(summary = "Get all users")
    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> allUsers() {
        List<UserDTO> users = userService.allUsers().stream().map(User::toDto).toList();
        return ResponseEntity.ok(users);
    }
}
