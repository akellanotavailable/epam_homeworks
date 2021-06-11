package com.epam.expositions.repository;

import com.epam.expositions.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {

    @Query(value = "INSERT hall_has_exposition(hall_id, exposition_id) VALUES(?1, ?2);",
            nativeQuery = true)
    void createHallReservation(Long hallId, Long expositionId);

}
