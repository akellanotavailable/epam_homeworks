package com.epam.expositions.controller;

import com.epam.expositions.entity.User;
import com.epam.expositions.exception.InvalidDataException;
import com.epam.expositions.security.SecurityUser;
import com.epam.expositions.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ChangePasswordController {
    private final UserService userService;
    private final PasswordEncoder encoder;

    @PostMapping("/changepassword")
    @PreAuthorize("hasAuthority('user')")
    public String changePassword(@AuthenticationPrincipal SecurityUser secuser, Model model) {

        String login = secuser.getUsername();
        model.addAttribute("login", login);

        User user = userService.findByLogin(login);

        String oldPassword = String.valueOf(model.getAttribute("password"));
        String newPassword = String.valueOf(model.getAttribute("newPassword"));
        String reNewPassword = String.valueOf(model.getAttribute("newRePassword"));

        if (!newPassword.equals(reNewPassword)) {
            throw new InvalidDataException("Passwords do not match");
        }

        oldPassword = encoder.encode(oldPassword);
        newPassword = encoder.encode(newPassword);

        if (!encoder.matches(encoder.encode(oldPassword), user.getPassword())) {
            throw new InvalidDataException("Wrong password");
        }

        user.setPassword(newPassword);

        userService.update(user, user.getId());

        return "redirect:/cabinet";
    }

}
