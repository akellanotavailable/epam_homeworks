package com.epam.expositions.service;


import com.epam.expositions.entity.Purchase;

import java.util.List;

public interface PurchaseService {

    List<Purchase> findByExpositionId(Long id);

    List<Purchase> findByUserId(Long id);

    List<Purchase> findAll();

    Purchase create(Purchase entity);

    Purchase update(Purchase entity);

    void deleteByUserId(Long id);


}
