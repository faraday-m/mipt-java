package edu.phystech;

import java.time.LocalDate;

public interface Account extends BankEntity {
    void addEntry(Transaction transaction);
    double balanceOn(LocalDate date);
}
