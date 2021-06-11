package com.epam.expositions.repository;

import com.epam.expositions.entity.Exposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpositionRepository extends JpaRepository<Exposition, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM exposition WHERE date_start < ?1 AND date_end > ?1")
    List<Exposition> findByDate(LocalDateTime date);
}
