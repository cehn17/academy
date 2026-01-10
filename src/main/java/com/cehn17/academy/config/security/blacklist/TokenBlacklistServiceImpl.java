package com.cehn17.academy.config.security.blacklist;

import com.cehn17.academy.user.service.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl {

    private final TokenBlacklistRepository repository;
    private final JwtService jwtService;

    public void blacklistToken(String token) {
        // Extraemos la fecha de expiración del token para guardarla
        Date expiration = jwtService.extractExpiration(token);

        TokenBlacklist blacklistToken = new TokenBlacklist();
        blacklistToken.setToken(token);
        // Convertimos Date a LocalDateTime
        blacklistToken.setExpirationDate(expiration.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime());

        repository.save(blacklistToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return repository.existsByToken(token);
    }
}
