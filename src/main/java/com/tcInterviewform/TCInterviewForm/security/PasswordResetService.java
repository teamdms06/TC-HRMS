package com.tcInterviewform.TCInterviewForm.security;

import com.tcInterviewform.TCInterviewForm.model.User;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetService {

    private static final long TOKEN_VALID_MINUTES = 5;
    private static final int TOKEN_BYTES = 32;

    private final SecureRandom secureRandom = new SecureRandom();
    private final Map<String, ResetToken> tokens = new ConcurrentHashMap<String, ResetToken>();

    public String createToken(User user) {
        removeExpiredTokens();
        byte[] bytes = new byte[TOKEN_BYTES];
        secureRandom.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        tokens.put(token, new ResetToken(user.getId(), Instant.now().plusSeconds(TOKEN_VALID_MINUTES * 60)));
        return token;
    }

    public Integer getUserIdIfValid(String token) {
        if (token == null) {
            return null;
        }
        ResetToken resetToken = tokens.get(token);
        if (resetToken == null || resetToken.expiresAt.isBefore(Instant.now())) {
            tokens.remove(token);
            return null;
        }
        return resetToken.userId;
    }

    public void consumeToken(String token) {
        tokens.remove(token);
    }

    private void removeExpiredTokens() {
        Instant now = Instant.now();
        Iterator<Map.Entry<String, ResetToken>> iterator = tokens.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ResetToken> entry = iterator.next();
            if (entry.getValue().expiresAt.isBefore(now)) {
                iterator.remove();
            }
        }
    }

    private static class ResetToken {

        private final Integer userId;
        private final Instant expiresAt;

        ResetToken(Integer userId, Instant expiresAt) {
            this.userId = userId;
            this.expiresAt = expiresAt;
        }
    }
}
