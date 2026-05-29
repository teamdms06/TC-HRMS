package com.tcInterviewform.TCInterviewForm.security;

import com.tcInterviewform.TCInterviewForm.model.User;
import com.tcInterviewform.TCInterviewForm.repository.USerRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private USerRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        if (user.getStatus() != null && !"ACTIVE".equalsIgnoreCase(user.getStatus())) {
            throw new DisabledException("User is not active");
        }
        String department = user.getDepartment() == null ? "USER" : user.getDepartment().trim().toUpperCase();
        String role = "ROLE_" + department.replaceAll("[^A-Z0-9]", "_");
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword() == null ? "" : user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(role)));
    }
}
