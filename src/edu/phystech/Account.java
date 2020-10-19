package edu.phystech;

import java.time.LocalDate;
import java.util.Collection;

public interface Account extends BankEntity {
    void addEntry(Entry entry);
    double balanceOn(LocalDate date);
    boolean add(double amount);
    boolean withdraw(double amount);
    Collection<Entry> history(LocalDate from, LocalDate to);
}
