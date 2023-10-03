package com.auth.repository;

import com.auth.model.RefreshTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokensRepository extends JpaRepository<RefreshTokens, Long> {

    public RefreshTokens findByRefreshToken(String refreshToken);
    public RefreshTokens findByUserId(Long userId);

}
