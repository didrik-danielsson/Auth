package com.hifresh.auth_service.controller;

import com.hifresh.auth_service.models.dtos.LoginUserDTO;
import com.hifresh.auth_service.models.dtos.RegisterUserDTO;
import com.hifresh.auth_service.models.dtos.UserDTO;
import com.hifresh.auth_service.models.dtos.responses.LoginResponse;
import com.hifresh.auth_service.models.enitities.User;
import com.hifresh.auth_service.service.AuthenticationService;
import com.hifresh.auth_service.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private JwtService jwtService;
    private AuthenticationService authenticationService;

    public AuthController(JwtService jwtService, AuthenticationService authService) {
        this.jwtService = jwtService;
        this.authenticationService = authService;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO request) {
        User registeredUser = authenticationService.registerUser(request);
        return ResponseEntity.ok(registeredUser);
    }
    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDTO loginUserData) {
        User authenticatedUser = authenticationService.authenticate(loginUserData);

        UserDTO user = UserDTO.builder()
                .email(authenticatedUser.getEmail())
                .fullName(authenticatedUser.getFullName())
                .userName(authenticatedUser.getUsername())
                .build();

        String token = jwtService.generateMyToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .user(user)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> authenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        UserDTO user = UserDTO.builder()
                .userName(userDetails.getUsername())
                .email(userDetails.getUsername())
                .build();

        return ResponseEntity.ok(user);
    }
}
