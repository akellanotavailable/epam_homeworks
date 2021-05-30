package com.epam.expositions.controller;

import com.epam.expositions.entity.Exposition;
import com.epam.expositions.entity.Hall;
import com.epam.expositions.entity.Status;
import com.epam.expositions.service.ExpositionService;
import com.epam.expositions.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReserveHallController {

    private final HallService hallService;
    private final ExpositionService expositionService;

    @PostMapping("/reservehall")
    @PreAuthorize("hasAuthority('user')")
    public String reserveHall(HttpSession session, Model model){
        Exposition exposition = (Exposition) session.getAttribute("exposition");
        session.removeAttribute("exposition");

        List<Hall> hallList = (List<Hall>) session.getAttribute("hallList");
        session.removeAttribute("hallList");

        String[] names = (String[]) model.getAttribute("hallCheck");
        List<String> checked = Arrays.asList(names.clone());
        hallList.forEach(hall -> {
            if(checked.contains(String.valueOf(hall.getId()))){
                hallService.createHallReservation(hall.getId(), exposition.getId());
            }
        });

        exposition.setStatusName(Status.PURCHASED.getName());
        expositionService.update(exposition, exposition.getId());

        return "redirect:/thanks";
    }
}

