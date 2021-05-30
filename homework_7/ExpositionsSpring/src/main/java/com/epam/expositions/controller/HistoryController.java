package com.epam.expositions.controller;

import com.epam.expositions.entity.Purchase;
import com.epam.expositions.security.SecurityUser;
import com.epam.expositions.service.ExpositionService;
import com.epam.expositions.service.PurchaseService;
import com.epam.expositions.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HistoryController {
    private final UserService userService;
    private final PurchaseService purchaseService;
    private final ExpositionService expositionService;

    @GetMapping("/history")
    @PreAuthorize("hasAuthority('user')")
    public String getPurchaseHistory(@AuthenticationPrincipal SecurityUser secuser, Model model){
        String login = secuser.getUsername();
        model.addAttribute("login", login);

        Long userId = userService.findByLogin(login).getId();
        List<Purchase> purchaseList = purchaseService.findByUserId(userId);
        HashMap<String, String> purchaseMap = new HashMap<>();
        for (Purchase purchase:
                purchaseList) {
            purchaseMap.put(expositionService.findById(purchase.getExpositionId()).getTopic(),
                    purchase.getStatus().getName());
        }

        model.addAttribute("purchaseMap",purchaseMap.entrySet());

        return "history";
    }
}
