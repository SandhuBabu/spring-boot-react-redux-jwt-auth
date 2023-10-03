package com.auth.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String refreshToken;

    @Column(unique = true)
    private Long userId;

    public RefreshTokens(String refreshToken, Long user_id) {
        this.refreshToken = refreshToken;
        this.userId = user_id;
    }
}
