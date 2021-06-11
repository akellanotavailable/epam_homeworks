package com.epam.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class CreditCard extends BillingDetails {

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "exp_year")
    private int expYear;

    @Column(name = "exp_month")
    private int expMonth;
}
