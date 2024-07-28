package com.example.backend.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationRequest {

    @NotEmpty
    @NotBlank
    private String firstName;

    @NotEmpty
    @NotBlank
    private String lastName;

    @NotEmpty
    @NotBlank
    private String email;

    @NotEmpty
    @NotBlank
    private String password;
}
