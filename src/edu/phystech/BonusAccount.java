package edu.phystech;

import java.time.LocalDate;

public class BonusAccount implements Account {
    @Override
    public void addEntry(Transaction transaction) {

    }

    @Override
    public double balanceOn(LocalDate date) {
        return 0;
    }

    @Override
    public boolean addCash(double amount) {
        return false;
    }

    @Override
    public boolean withdrawCash(double amount) {
        return false;
    }
}
