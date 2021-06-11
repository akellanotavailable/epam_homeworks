package com.epam.expositions.controller;

import com.epam.expositions.dto.RegisterDTO;
import com.epam.expositions.entity.Role;
import com.epam.expositions.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService registerService;

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("register", new RegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute @NotNull RegisterDTO register, HttpServletRequest request) {
        registerService.register(register);

        HttpSession session = request.getSession(true);
        session.setAttribute("login", register.getLogin());
        session.setAttribute("role", Role.USER);

        return "redirect:/login";
    }
}
