package com.auth.services;

import com.auth.dto.AuthResponse;
import com.auth.dto.SignInRequest;
import com.auth.model.RefreshTokens;
import com.auth.repository.RefreshTokensRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.dto.SignupRequest;
import com.auth.model.Role;
import com.auth.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokensRepository tokensRepository;

    public Object signup(SignupRequest signupRequest) {
        AuthResponse authResponse = null;

        try {
            var user = User.builder()
                    .email(signupRequest.getEmail())
                    .firstName(signupRequest.getFirstName())
                    .lastName(signupRequest.getLastName())
                    .password(passwordEncoder.encode(signupRequest.getPassword()))
                    .role(Role.ROLE_USER)
                    .build();
            user = userService.saveUser(user);
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            tokensRepository.save(new RefreshTokens(refreshToken, user.getId()));

            authResponse = AuthResponse.builder()
                    .username(user.getFirstName() + " "+user.getLastName())
                    .email(user.getEmail())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            return authResponse;
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cause = (ConstraintViolationException) e.getCause();
                if (cause.getLocalizedMessage().contains("Duplicate"))
                    return "Email address is already used.";
            }
            return authResponse;
        }
    }

    public AuthResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        AuthResponse response = null;

       try {
           var user = userService.getUserByEmail(request.getEmail());
           String accessToken = jwtService.generateAccessToken(user);
           String refreshToken = jwtService.generateRefreshToken(user);

           RefreshTokens savedRefreshToken = tokensRepository.findByUserId(user.getId());
           if(savedRefreshToken != null) {
               savedRefreshToken.setRefreshToken(refreshToken);
               tokensRepository.save(savedRefreshToken);
           } else {
               tokensRepository.save(new RefreshTokens(refreshToken, user.getId()));
           }


           response = AuthResponse.builder()
                   .username(user.getFirstName() + " "+user.getLastName())
                   .email(user.getEmail())
                   .accessToken(accessToken)
                   .refreshToken(refreshToken)
                   .build();
           return response;
       } catch (Exception e) {
           return null;
       }
    }

    public AuthResponse refresh(String token) {

        AuthResponse authResponse = null;
        try {

            /*
            *  need to check whether the user id of token saved in db and
            *  loaded user's id are equal
            *  else throw exception
            * */


            String email = jwtService.extractUsername(token);
            User user = userService.getUserByEmail(email);

            if(user!=null) {
                RefreshTokens tokenSaved = tokensRepository.findByRefreshToken(token);
                if(tokenSaved == null || (tokenSaved.getUserId() != user.getId()))
                    throw new Exception("Invalid Refresh Token");

               String newAccessToken = jwtService.generateAccessToken(user);
               return AuthResponse.builder()
                       .email(email)
                       .username(user.getFirstName() + " "+ user.getLastName())
                       .accessToken(newAccessToken)
                       .refreshToken(token)
                       .build();
            }
        } catch (Exception e) {
            return null;
        }
        return authResponse;
    }

    public String logout(String email) {
        String response = null;
        try {
            Long userId = userService.getUserByEmail(email).getId();
            RefreshTokens savedRefreshToken = tokensRepository.findByUserId(userId);
            if(savedRefreshToken != null) {
                tokensRepository.delete(savedRefreshToken);
                response = "Logout Success";
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return response;
        }
    }
}
