package com.hifresh.auth_service.controller;

import com.hifresh.auth_service.models.dtos.RegisterUserDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @PostMapping("/register")
    public String register(@RequestBody String request) {
        System.out.println(request);
        return "Signup";
    }

    @GetMapping("/profile")
    public String getMe(@RequestBody String request) {
        System.out.println("Profile");
        return "Profile";
    }

}
