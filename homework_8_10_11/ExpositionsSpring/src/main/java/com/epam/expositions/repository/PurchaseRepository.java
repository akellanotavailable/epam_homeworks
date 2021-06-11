package com.epam.expositions.repository;

import com.epam.expositions.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query(value = "SELECT p FROM Purchase p WHERE p.id.exposition.id = ?1")
    List<Purchase> findByExpositionId(Long id);
    @Query(value = "SELECT p FROM Purchase p WHERE p.id.user.id = ?1")
    List<Purchase> findByUserId(Long id);
    @Query(value = "DELETE FROM Purchase p WHERE p.id.user.id = ?1")
    void deleteByUserId(Long id);

}
