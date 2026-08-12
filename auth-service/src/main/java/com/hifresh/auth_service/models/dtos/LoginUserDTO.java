package com.hifresh.auth_service.models.dtos;

import lombok.Data;

@Data
public class LoginUserDTO {
    private String email;
    private String password;
}
