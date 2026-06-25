package com.lqo.modules.auth.application;

import com.lqo.modules.auth.domain.AppUser;
import com.lqo.modules.auth.infrastructure.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AppUserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            AppUserRepository users,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public LoginResult login(String username, String password) {
        AppUser user = users.findByUsername(username)
                .filter(AppUser::isEnabled)
                .orElseThrow(AuthenticationFailedException::new);

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new AuthenticationFailedException();
        }

        JwtService.TokenDetails tokenDetails = jwtService.createToken(user.getUsername());
        return new LoginResult(
                tokenDetails.token(),
                "Bearer",
                tokenDetails.expiresInSeconds(),
                tokenDetails.expiresAt().toString(),
                user.getUsername()
        );
    }

    public record LoginResult(
            String accessToken,
            String tokenType,
            long expiresIn,
            String expiresAt,
            String username
    ) {
    }
}
