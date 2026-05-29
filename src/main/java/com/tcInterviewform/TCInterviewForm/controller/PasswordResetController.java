package com.tcInterviewform.TCInterviewForm.controller;

import com.tcInterviewform.TCInterviewForm.model.User;
import com.tcInterviewform.TCInterviewForm.repository.USerRepository;
import com.tcInterviewform.TCInterviewForm.security.PasswordResetService;
import com.tcInterviewform.TCInterviewForm.security.PasswordService;
import java.util.Optional;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetController {

    @Autowired
    private USerRepository userRepository;

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String sendResetLink(@RequestParam("email") String email, HttpServletRequest request, Model model) {
        if (StringUtils.hasText(email)) {
            User user = findPasswordResetUser(email.trim());
            if (user != null) {
                String token = passwordResetService.createToken(user);
                String resetLink = buildResetLink(request, token);
                try {
                    sendResetPasswordEmail(user, resetLink);
                } catch (MessagingException ex) {
                    model.addAttribute("error", "Unable to send reset link. Please try again.");
                    return "forgot-password";
                }
            }
        }
        model.addAttribute("msg", "If this email exists, a reset link has been sent. Link is valid for 5 minutes.");
        return "forgot-password";
    }

    private User findPasswordResetUser(String email) {
        List<User> users = userRepository.findByEmail(email);
        if (users == null || users.isEmpty()) {
            return null;
        }
        for (User user : users) {
            if (user.getStatus() != null && "ACTIVE".equalsIgnoreCase(user.getStatus())) {
                return user;
            }
        }
        return users.get(0);
    }

    @GetMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, Model model) {
        Integer userId = passwordResetService.getUserIdIfValid(token);
        if (userId == null) {
            model.addAttribute("error", "Reset link is invalid or expired.");
            return "reset-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String updatePassword(
            @RequestParam("token") String token,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {
        Integer userId = passwordResetService.getUserIdIfValid(token);
        if (userId == null) {
            model.addAttribute("error", "Reset link is invalid or expired.");
            return "reset-password";
        }
        if (!StringUtils.hasText(password) || password.length() < 8) {
            model.addAttribute("token", token);
            model.addAttribute("error", "Password must be at least 8 characters.");
            return "reset-password";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("token", token);
            model.addAttribute("error", "Password and confirm password do not match.");
            return "reset-password";
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            model.addAttribute("error", "User not found.");
            return "reset-password";
        }

        User user = userOptional.get();
        user.setPassword(passwordService.encodePassword(password));
        userRepository.save(user);
        passwordResetService.consumeToken(token);

        model.addAttribute("msg", "Password updated successfully. Please login.");
        return "login";
    }

    private String buildResetLink(HttpServletRequest request, String token) {
        String requestUrl = request.getRequestURL().toString();
        String baseUrl = requestUrl.substring(0, requestUrl.length() - request.getRequestURI().length())
                + request.getContextPath();
        return baseUrl + "/reset-password?token=" + token;
    }

    private void sendResetPasswordEmail(User user, String resetLink) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        message.setFrom("teamdms1@theconnectionsindia.com");
        message.setTo(user.getEmail());
        message.setSubject("HRMS Password Reset Link");
        String name = user.getName() == null ? "User" : user.getName();
        String body = "<p>Dear " + name + ",</p>"
                + "<p>Click the link below to reset your HRMS password. This link is valid for 5 minutes.</p>"
                + "<p><a href=\"" + resetLink + "\">Reset Password</a></p>"
                + "<p>If you did not request this, please ignore this email.</p>";
        message.setText(body, true);
        mailSender.send(mimeMessage);
    }
}
