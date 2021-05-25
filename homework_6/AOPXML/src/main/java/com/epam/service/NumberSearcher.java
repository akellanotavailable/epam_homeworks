package com.epam.service;

import com.epam.annotation.Logged;


public class NumberSearcher {

    @Logged
    public int searchForNumber(int numb) {
        for (int i = 0; i < numb + 1; i++) {
            if (i == numb) {
                return i;
            }
        }
        return 0;
    }
}
