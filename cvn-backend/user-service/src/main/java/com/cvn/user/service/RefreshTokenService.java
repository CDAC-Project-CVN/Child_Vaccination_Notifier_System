package com.cvn.user.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cvn.user.entity.RefreshToken;
import com.cvn.user.entity.User;
import com.cvn.user.exception.InvalidTokenException;
import com.cvn.user.repository.RefreshTokenRepository;
import com.cvn.user.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    @Value("${jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public RefreshToken createRefreshToken(User user) {

        String token = jwtService.generateRefreshToken(user);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiresAt(
                LocalDateTime.now()
                        .plusSeconds(refreshTokenExpiration / 1000));
        refreshToken.setRevoked(false);

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken =
                refreshTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new InvalidTokenException(
                                        "Refresh token not found"));

        if (Boolean.TRUE.equals(refreshToken.getRevoked())) {
            throw new InvalidTokenException("Refresh token is revoked");
        }

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException("Refresh token has expired");
        }

        return refreshToken;
    }

    public void revokeRefreshToken(String token) {

        RefreshToken refreshToken = verifyRefreshToken(token);

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }

    public void revokeAllUserTokens(User user) {

        List<RefreshToken> tokens =
                refreshTokenRepository.findByUserAndRevokedFalse(user);

        tokens.forEach(token -> token.setRevoked(true));

        refreshTokenRepository.saveAll(tokens);
    }
}