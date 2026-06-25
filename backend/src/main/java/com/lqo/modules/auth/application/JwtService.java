package com.lqo.modules.auth.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final Base64.Encoder BASE64_URL_ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder BASE64_URL_DECODER = Base64.getUrlDecoder();
    private static final String HMAC_SHA256 = "HmacSHA256";
    private static final TypeReference<Map<String, Object>> MAP_TYPE = new TypeReference<>() {
    };

    private final JwtProperties properties;
    private final ObjectMapper objectMapper;
    private final Clock clock;

    public JwtService(JwtProperties properties, ObjectMapper objectMapper) {
        this.properties = properties;
        this.objectMapper = objectMapper;
        this.clock = Clock.systemUTC();
    }

    public TokenDetails createToken(String username) {
        Instant issuedAt = Instant.now(clock);
        Instant expiresAt = issuedAt.plusSeconds(properties.expirationMinutes() * 60L);

        Map<String, Object> header = new LinkedHashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("sub", username);
        payload.put("iat", issuedAt.getEpochSecond());
        payload.put("exp", expiresAt.getEpochSecond());

        String unsignedToken = encodeJson(header) + "." + encodeJson(payload);
        String signature = sign(unsignedToken);

        return new TokenDetails(unsignedToken + "." + signature, expiresAt, properties.expirationMinutes() * 60L);
    }

    public String validateAndGetSubject(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid token");
        }

        String unsignedToken = parts[0] + "." + parts[1];
        if (!constantTimeEquals(sign(unsignedToken), parts[2])) {
            throw new IllegalArgumentException("Invalid token signature");
        }

        Map<String, Object> payload = decodeJson(parts[1]);
        long expiresAt = ((Number) payload.get("exp")).longValue();
        if (Instant.now(clock).isAfter(Instant.ofEpochSecond(expiresAt))) {
            throw new IllegalArgumentException("Expired token");
        }

        Object subject = payload.get("sub");
        if (!(subject instanceof String username) || username.isBlank()) {
            throw new IllegalArgumentException("Invalid token subject");
        }

        return username;
    }

    private String encodeJson(Map<String, Object> content) {
        try {
            return BASE64_URL_ENCODER.encodeToString(objectMapper.writeValueAsBytes(content));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to encode JWT", exception);
        }
    }

    private Map<String, Object> decodeJson(String content) {
        try {
            return objectMapper.readValue(BASE64_URL_DECODER.decode(content), MAP_TYPE);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Invalid token payload", exception);
        }
    }

    private String sign(String content) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            mac.init(new SecretKeySpec(properties.secret().getBytes(StandardCharsets.UTF_8), HMAC_SHA256));
            return BASE64_URL_ENCODER.encodeToString(mac.doFinal(content.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to sign JWT", exception);
        }
    }

    private boolean constantTimeEquals(String expected, String actual) {
        byte[] expectedBytes = expected.getBytes(StandardCharsets.UTF_8);
        byte[] actualBytes = actual.getBytes(StandardCharsets.UTF_8);
        if (expectedBytes.length != actualBytes.length) {
            return false;
        }

        int result = 0;
        for (int index = 0; index < expectedBytes.length; index++) {
            result |= expectedBytes[index] ^ actualBytes[index];
        }
        return result == 0;
    }

    public record TokenDetails(String token, Instant expiresAt, long expiresInSeconds) {
    }

    @ConfigurationProperties(prefix = "security.jwt")
    public record JwtProperties(String secret, int expirationMinutes) {
    }
}
