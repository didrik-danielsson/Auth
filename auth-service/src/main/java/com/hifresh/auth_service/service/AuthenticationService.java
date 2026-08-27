package com.hifresh.auth_service.service;

import com.hifresh.auth_service.models.dtos.LoginUserDTO;
import com.hifresh.auth_service.models.dtos.RegisterUserDTO;
import com.hifresh.auth_service.models.enitities.User;
import com.hifresh.auth_service.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//TODO: Validate password before registering account
    public User registerUser(RegisterUserDTO input) {
        User user = User.builder()
                .fullName(input.getFullName())
                .email(input.getEmail())
                .userName(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .build();
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDTO input) {
    authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(
                    input.getEmail(),
                    input.getPassword()
            ));
    return userRepository.findByEmail(input.getEmail())
            .orElseThrow();
}
}
