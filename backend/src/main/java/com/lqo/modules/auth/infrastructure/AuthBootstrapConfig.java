package com.lqo.modules.auth.infrastructure;

import com.lqo.modules.auth.domain.AppUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties(AuthBootstrapConfig.AuthBootstrapProperties.class)
public class AuthBootstrapConfig {

    @Bean
    CommandLineRunner authBootstrapRunner(
            AuthBootstrapProperties properties,
            AppUserRepository users,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (!properties.enabled() || users.existsByUsername(properties.username())) {
                return;
            }

            users.save(new AppUser(
                    properties.username(),
                    passwordEncoder.encode(properties.password())
            ));
        };
    }

    @ConfigurationProperties(prefix = "auth.bootstrap")
    public record AuthBootstrapProperties(
            boolean enabled,
            String username,
            String password
    ) {
    }
}
