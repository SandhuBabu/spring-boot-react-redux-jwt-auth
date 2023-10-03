package com.auth.controller;



import com.auth.dto.AuthResponse;
import com.auth.dto.SignInRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.dto.SignupRequest;
import com.auth.dto.SingleToken;
import com.auth.services.AuthService;

import lombok.RequiredArgsConstructor;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequest signupRequest) {
        var response = authService.signup(signupRequest);
        
        if(response == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        else if (response instanceof String)
            return ResponseEntity.status(HttpStatus.CONFLICT).body((String)response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody SignInRequest request) {
        var response = authService.signin(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody SingleToken tokenDto){
        AuthResponse response = authService.refresh(tokenDto.getToken());
        if(response == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(Principal principal) {
        var res = authService.logout(principal.getName());
        if(res == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(res);
    }
}
