package com.cvn.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cvn.user.entity.RefreshToken;
import com.cvn.user.entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
//	Optional<RefreshToken> findByTokenHash(String tokenHash);

//    void deleteByUser(User user);
    
    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findByUserAndRevokedFalse(User user);
}
