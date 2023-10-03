package com.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class SignupRequest {
    String firstName;
    String lastName;
    String email;
    String password;
    
}
