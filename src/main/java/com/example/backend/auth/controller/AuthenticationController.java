package com.example.backend.auth.controller;

import com.example.backend.auth.jwt.service.JwtService;
import com.example.backend.auth.request.AuthenticationRequest;
import com.example.backend.auth.request.RegistrationRequest;
import com.example.backend.auth.response.AuthenticationResponse;
import com.example.backend.auth.service.AuthenticationService;
import com.example.backend.auth.service.UserDetailsServiceImpl;
import com.example.backend.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(request.getEmail());
        String jwtToken = jwtService.generateToken((User) userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<AuthenticationResponse> loginOAuth2Success(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String jwtToken = jwtService.generateToken(oAuth2User);
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }
}
