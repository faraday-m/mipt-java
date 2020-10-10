package edu.phystech;

import java.time.LocalDate;

public interface Account extends BankEntity {
    void addEntry(Entry entry);

    double balanceOn(LocalDate date);

    boolean add(double amount);
    boolean withdraw(double amount);
}
