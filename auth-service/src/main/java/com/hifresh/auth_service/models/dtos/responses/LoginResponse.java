package com.hifresh.auth_service.models.dtos.responses;

import com.hifresh.auth_service.models.dtos.UserDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private String token;
    private long expiresIn;
    private UserDTO user;
}
