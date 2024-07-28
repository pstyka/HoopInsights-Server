package com.example.backend.auth.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthenticationRequest {
    private String email;
    private String password;
}
