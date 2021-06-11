package com.epam.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class BankAccount extends BillingDetails {

    @Column(name = "account")
    private String account;

    @Column(name = "bank_name")
    private String bankName;
}
