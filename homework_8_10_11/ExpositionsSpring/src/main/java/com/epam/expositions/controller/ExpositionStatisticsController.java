package com.epam.expositions.controller;

import com.epam.expositions.entity.Purchase;
import com.epam.expositions.entity.User;
import com.epam.expositions.service.PurchaseService;
import com.epam.expositions.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ExpositionStatisticsController {
    private final PurchaseService purchaseService;
    private final UserService userService;

    @GetMapping("/expositionstats")
    @PreAuthorize("hasAuthority('client')")
    public String getExpositionStats(Model model) {
        String expositionId = String.valueOf(model.getAttribute("expositionid"));
        List<Purchase> purchaseList = purchaseService.findByExpositionId(Long.parseLong(expositionId));
        List<User> userList = purchaseList.stream()
                .map(purchase -> purchase.getId().getUser())
                .collect(Collectors.toList());
        model.addAttribute("userList", userList);
        return "expostats";
    }

    @PostMapping("/expositionstats")
    @PreAuthorize("hasAuthority('client')")
    public String deleteUserFromExpositionStats(Model model) {
        purchaseService.deleteByUserId(
                Long.parseLong(
                        String.valueOf(
                                model.getAttribute("userId")
                        )
                )
        );
        return "redirect:/expositionstats";
    }
}
