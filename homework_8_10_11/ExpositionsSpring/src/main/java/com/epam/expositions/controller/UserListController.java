package com.epam.expositions.controller;

import com.epam.expositions.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserListController {

    private final UserService userService;

    @GetMapping("/userlist")
    @PreAuthorize("hasAuthority('admin')")
    public String getAllUsers(Model model){
        model.addAttribute("userList", userService.findALL());
        return "userlist";
    }

    @PostMapping("/userlist")
    @PreAuthorize("hasAuthority('admin')")
    public String removeUser(Model model){
        Long id = (Long) model.getAttribute("userid");
        userService.deleteById(id);
        return "userlist";
    }

}
