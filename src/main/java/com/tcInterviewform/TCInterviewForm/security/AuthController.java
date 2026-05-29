package com.tcInterviewform.TCInterviewForm.security;

import com.tcInterviewform.TCInterviewForm.model.User;
import com.tcInterviewform.TCInterviewForm.repository.USerRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private USerRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (!passwordService.verifyPassword(user, loginRequest.getPassword())
                || (user.getStatus() != null && !"ACTIVE".equalsIgnoreCase(user.getStatus()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        UsernamePasswordAuthenticationToken authentication = createAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        System.out.println("Generated JWT: " + jwt);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    private UsernamePasswordAuthenticationToken createAuthentication(User user) {
        String department = user.getDepartment() == null ? "USER" : user.getDepartment().trim().toUpperCase();
        String roleName = "ROLE_" + department.replaceAll("[^A-Z0-9]", "_");
        return new UsernamePasswordAuthenticationToken(
                user.getUsername(), null, Arrays.asList(new SimpleGrantedAuthority(roleName)));
    }
}
