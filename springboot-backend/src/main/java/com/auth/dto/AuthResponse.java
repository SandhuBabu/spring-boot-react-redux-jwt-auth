package com.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String username;
    private String email;
    private String accessToken;
    private String refreshToken;
}
