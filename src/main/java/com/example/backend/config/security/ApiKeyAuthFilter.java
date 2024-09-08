package com.example.backend.config.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import jakarta.servlet.http.HttpServletRequest;

import static com.example.backend.config.security.SecurityConfig.API_KEY;

public class ApiKeyAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final String principalRequestHeader;

    public ApiKeyAuthFilter(String principalRequestHeader) {
        this.principalRequestHeader = principalRequestHeader;
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String apiKey = request.getHeader(principalRequestHeader);
        if (API_KEY.equals(apiKey)) {
            return new SimpleGrantedAuthority(apiKey);
        } else {
            return null;
        }
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
}

