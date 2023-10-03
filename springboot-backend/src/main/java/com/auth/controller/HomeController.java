package com.auth.controller;

import com.auth.dto.UserDto;
import com.auth.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Object> home(Principal principal) {
        String email = principal.getName();
        var user = userService.getUserByEmail(email);

        UserDto res = UserDto.builder()
                .username(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .build();
        return ResponseEntity.ok(res);
    }
}
