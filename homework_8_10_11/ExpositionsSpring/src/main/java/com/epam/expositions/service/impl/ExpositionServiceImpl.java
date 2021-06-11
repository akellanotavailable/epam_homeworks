package com.epam.expositions.service.impl;

import com.epam.expositions.dto.ExpositionDTO;
import com.epam.expositions.entity.Exposition;
import com.epam.expositions.repository.ExpositionRepository;
import com.epam.expositions.service.ExpositionService;
import com.epam.expositions.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.epam.expositions.util.Converter.convertLocalDateTimeToSQLDateString;

@Service
@RequiredArgsConstructor
public class ExpositionServiceImpl implements ExpositionService {
    private final ExpositionRepository expositionRepository;

    @Override
    public Exposition findById(Long id) {
        return expositionRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public List<ExpositionDTO> findByDate(LocalDateTime date) {
        List<Exposition> expositions = expositionRepository.findByDate(date);
        List<ExpositionDTO> expositionDTOS = new ArrayList<>();
        for (Exposition e :
                expositions) {
            expositionDTOS.add(ExpositionDTO.builder()
                    .id(e.getId())
                    .topic(e.getTopic())
                    .dateStart(convertLocalDateTimeToSQLDateString(e.getDateStart()))
                    .dateEnd(convertLocalDateTimeToSQLDateString(e.getDateEnd()))
                    .price(e.getPrice())
                    .capacity(e.getCapacity())
                    .imagePath(e.getImagePath())
                    .detailsLink(e.getDetailsLink())
                    .build());
        }
        return expositionDTOS;
    }

    @Override
    public List<ExpositionDTO> findByHostId(Long id) {
        List<ExpositionDTO> expositionDTOS = new ArrayList<>();
        List<Exposition> expositions = expositionRepository.findAll();
        expositions.forEach(exposition -> {
            if(exposition.getHost().getId().equals(id)){
                expositionDTOS.add(ExpositionDTO.builder()
                        .id(exposition.getId())
                        .topic(exposition.getTopic())
                        .dateStart(Converter.parseExpositionDateToDTOString(exposition.getDateStart()))
                        .dateEnd(Converter.parseExpositionDateToDTOString(exposition.getDateEnd()))
                        .price(exposition.getPrice())
                        .capacity(exposition.getCapacity())
                        .imagePath(exposition.getImagePath())
                        .detailsLink(exposition.getDetailsLink())
                        .isActive(exposition.getIsActive())
                        .build());
            }
        });
        return expositionDTOS;
    }

    @Override
    public List<ExpositionDTO> findALL(boolean showLegacy) {
        List<ExpositionDTO> expositionDTOS = new ArrayList<>();
        List<Exposition> expositions = expositionRepository.findAll();
        for (Exposition item :
                expositions) {

            if (!showLegacy && item.getIsActive()) {
                expositionDTOS.add(ExpositionDTO.builder()
                        .id(item.getId())
                        .topic(item.getTopic())
                        .dateStart(Converter.parseExpositionDateToDTOString(item.getDateStart()))
                        .dateEnd(Converter.parseExpositionDateToDTOString(item.getDateEnd()))
                        .price(item.getPrice())
                        .capacity(item.getCapacity())
                        .imagePath(item.getImagePath())
                        .detailsLink(item.getDetailsLink())
                        .isActive(item.getIsActive())
                        .build());
            }

            if (showLegacy) {
                expositionDTOS.add(ExpositionDTO.builder()
                        .id(item.getId())
                        .topic(item.getTopic())
                        .dateStart(Converter.parseExpositionDateToDTOString(item.getDateStart()))
                        .dateEnd(Converter.parseExpositionDateToDTOString(item.getDateEnd()))
                        .price(item.getPrice())
                        .capacity(item.getCapacity())
                        .imagePath(item.getImagePath())
                        .detailsLink(item.getDetailsLink())
                        .isActive(item.getIsActive())
                        .build());
            }

        }
        return expositionDTOS;
    }

    @Override
    public Exposition create(Exposition entity) {
        return expositionRepository.save(entity);
    }

    @Override
    public Exposition update(Exposition entity) {
        return expositionRepository.save(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean delete(Exposition entity) {
        return false;
    }
}
