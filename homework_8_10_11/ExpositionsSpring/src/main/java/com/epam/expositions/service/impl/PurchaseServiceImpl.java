package com.epam.expositions.service.impl;

import com.epam.expositions.entity.Purchase;
import com.epam.expositions.repository.PurchaseRepository;
import com.epam.expositions.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @Override
    public List<Purchase> findByExpositionId(Long id) {
        return purchaseRepository.findByExpositionId(id);
    }

    @Override
    public List<Purchase> findByUserId(Long id) {
        return purchaseRepository.findByUserId(id);
    }

    @Override
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    @Override
    public Purchase create(Purchase entity) {
        return purchaseRepository.save(entity);
    }

    @Override
    public Purchase update(Purchase entity) {
        return purchaseRepository.save(entity);
    }

    @Override
    public void deleteByUserId(Long id) {
        purchaseRepository.deleteByUserId(id);
    }

}
