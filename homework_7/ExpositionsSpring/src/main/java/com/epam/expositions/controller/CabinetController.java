package com.epam.expositions.controller;

import com.epam.expositions.entity.User;
import com.epam.expositions.security.SecurityUser;
import com.epam.expositions.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CabinetController {
    private final UserService userService;

    @GetMapping("/cabinet")
    @PreAuthorize("hasAuthority('user')")
    public String cabinetLogin(@AuthenticationPrincipal SecurityUser secuser, Model model){

        String login = secuser.getUsername();
        model.addAttribute("login", login);

        User user = userService.findByLogin(login);
        model.addAttribute("role", user.getRole().getName());

        if (user.getRole().getName().equals("admin")) {
            model.addAttribute("userList", userService.findALL());
        }
        model.addAttribute("userData", userService.findByLogin(login));

        return "cabinet";
    }

}
