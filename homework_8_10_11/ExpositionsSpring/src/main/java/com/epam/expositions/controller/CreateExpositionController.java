package com.epam.expositions.controller;

import com.epam.expositions.dto.DurationDTO;
import com.epam.expositions.dto.HallNameDTO;
import com.epam.expositions.dto.HallTimetableDTO;
import com.epam.expositions.entity.Exposition;
import com.epam.expositions.entity.Hall;
import com.epam.expositions.entity.Role;
import com.epam.expositions.entity.User;
import com.epam.expositions.exception.InvalidDataException;
import com.epam.expositions.security.SecurityUser;
import com.epam.expositions.service.ExpositionService;
import com.epam.expositions.service.HallService;
import com.epam.expositions.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CreateExpositionController {

    private final ExpositionService expositionService;
    private final UserService userService;
    private final HallService hallService;

    @GetMapping("/newexposition")
    @PreAuthorize("hasAuthority('user')")
    public String newExposition(Model model) {
        model.addAttribute("exposition", new Exposition());
        return "newexpo";
    }

    @PostMapping("/newexposition")
    @PreAuthorize("hasAuthority('user')")
    public String createNewExposition(@AuthenticationPrincipal SecurityUser secuser, Model model,
                                      @ModelAttribute Exposition exposition, HttpSession session) {
        String login = secuser.getUsername();
        model.addAttribute("login", login);

        User user = userService.findByLogin(login);

        if (exposition.getDateStart().isAfter(exposition.getDateEnd())) {
            throw new InvalidDataException("Start date is after end date.");
        }

        if (exposition.getCapacity() <= 0) {
            throw new InvalidDataException("Please enter valid number of tickets.");
        }

        if (exposition.getPrice().doubleValue() < 0) {
            throw new InvalidDataException("Please enter valid price.");
        }

        exposition.setHost(user);
        exposition.setIsActive(true);

        expositionService.create(exposition);

        if (!user.getRole().toString().toLowerCase(Locale.ROOT).equals("client") ||
                !user.getRole().toString().toLowerCase(Locale.ROOT).equals("admin")) {
            user.setRole(Role.CLIENT);
            userService.update(user, user.getId());
        }

        session.setAttribute("exposition", exposition);

        DurationDTO reservationTime = new DurationDTO(exposition.getDateStart(), exposition.getDateEnd());

        List<HallTimetableDTO> hallTimetableList = hallService.findAll();

        hallTimetableList = hallTimetableList.stream()
                .filter(hallTimetableDTO ->
                        hallTimetableDTO.getReservationDateTime().stream().allMatch(unavailableTime ->
                                //if start time is within reserved time
                                !(reservationTime.getDateStart().isAfter(unavailableTime.getDateStart()) &&
                                        reservationTime.getDateStart().isBefore(unavailableTime.getDateEnd()))
                                        &&
                                        //if end time is within reserved time
                                        !(reservationTime.getDateEnd().isBefore(unavailableTime.getDateEnd()) &&
                                                reservationTime.getDateEnd().isAfter(unavailableTime.getDateStart()))))
                .collect(Collectors.toList());

        List<Hall> hallList = hallTimetableList.stream()
                .map(HallTimetableDTO::getHall)
                .collect(Collectors.toList());

        session.setAttribute("hallList", hallList);
        model.addAttribute("hallNameList", new HallNameDTO());

        return "newexpohall";
    }
}
