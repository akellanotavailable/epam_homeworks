package com.epam.expositions.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Embeddable
public class PurchasePK implements Serializable {
    @ManyToOne
    private Exposition exposition;
    @ManyToOne
    private User user;
}
