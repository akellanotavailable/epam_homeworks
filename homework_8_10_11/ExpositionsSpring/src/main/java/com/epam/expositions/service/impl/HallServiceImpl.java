package com.epam.expositions.service.impl;

import com.epam.expositions.dto.DurationDTO;
import com.epam.expositions.dto.HallTimetableDTO;
import com.epam.expositions.entity.Hall;
import com.epam.expositions.repository.HallRepository;
import com.epam.expositions.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {
    private final HallRepository hallRepository;

    @Override
    public List<HallTimetableDTO> findAll() {
        List<Hall> halls = hallRepository.findAll();

        List<HallTimetableDTO> hallTimetables = new ArrayList<>();

        halls.forEach(hall -> {
            List<DurationDTO> durationsList = new ArrayList<>();

            hall.getExpositions().forEach(exposition -> {
                durationsList.add(new DurationDTO(exposition.getDateStart(), exposition.getDateEnd()));
            });
            hallTimetables.add(new HallTimetableDTO(hall, durationsList));
        });
        return hallTimetables;
}

    @Override
    public HallTimetableDTO findById(Long id) {
        List<HallTimetableDTO> hallTimetables = findAll();
        return hallTimetables.stream()
                .filter(hallTimetable ->
                        hallTimetable.getHall().getId().equals(id))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public List<DurationDTO> getTimeUnavailable(Hall hall) {
        return findById(hall.getId()).getReservationDateTime();
    }

    @Override
    public HallTimetableDTO create(HallTimetableDTO entity) {
        return null;
    }

    @Override
    public HallTimetableDTO update(HallTimetableDTO entity, Long id) {
        return null;
    }

    @Override
    public boolean delete(HallTimetableDTO entity) {
        return false;
    }

    public void createHallReservation(Long hallId, Long expositionId) {
        hallRepository.createHallReservation(hallId, expositionId);
    }
}
