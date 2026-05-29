package com.tcInterviewform.TCInterviewForm.security;

import com.tcInterviewform.TCInterviewForm.model.User;
import com.tcInterviewform.TCInterviewForm.repository.USerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PasswordService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private USerRepository userRepository;

    public boolean verifyPassword(User user, String rawPassword) {
        if (user == null || !StringUtils.hasText(rawPassword)) {
            return false;
        }

        String storedPassword = user.getPassword();
        if (!StringUtils.hasText(storedPassword)) {
            return false;
        }

        if (isBcrypt(storedPassword)) {
            return passwordEncoder.matches(rawPassword, storedPassword);
        }

        if (storedPassword.equals(rawPassword)) {
            user.setPassword(passwordEncoder.encode(rawPassword));
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    private boolean isBcrypt(String password) {
        return password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$");
    }
}
