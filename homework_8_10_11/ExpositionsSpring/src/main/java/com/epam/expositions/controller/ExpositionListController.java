package com.epam.expositions.controller;

import com.epam.expositions.dto.ExpositionDTO;
import com.epam.expositions.entity.Exposition;
import com.epam.expositions.entity.User;
import com.epam.expositions.security.SecurityUser;
import com.epam.expositions.service.ExpositionService;
import com.epam.expositions.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExpositionListController {
    private final ExpositionService expositionService;
    private final UserService userService;

    @GetMapping("/expositionlist")
    @PreAuthorize("hasAuthority('client')")
    public String getExpositionList(@AuthenticationPrincipal SecurityUser secuser,  Model model){

        String login = secuser.getUsername();
        model.addAttribute("login", login);

        User user = userService.findByLogin(login);
        List<ExpositionDTO> expositionDTOS = expositionService.findByHostId(user.getId());

        model.addAttribute("expositionList", expositionDTOS);

        return "expolist";
    }

    @GetMapping("/expositionlistdelete")
    @PreAuthorize("hasAuthority('client')")
    public String refundExposition(@RequestParam Long expositionid){
        Exposition e = expositionService.findById(expositionid);
        e.setIsActive(false);
        expositionService.update(e);
        return "redirect:/expositionlist";
    }
}
