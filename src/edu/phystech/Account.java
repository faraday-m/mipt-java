package edu.phystech;

import java.time.LocalDate;

public interface Account {
    void addEntry(Transaction transaction);
    double balanceOn(LocalDate date);
    boolean addCash(double amount);
    boolean withdrawCash(double amount);
}
