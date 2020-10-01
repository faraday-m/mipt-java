package edu.phystech;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Account {
    private final long id;
    private final Entries entries;
    private double balance;

    public Account(long id) {
        this.id = id;
        this.entries = new Entries(this);
        this.balance = 0;
    }


    public Transaction getTransaction(long id) {
        return TransactionManager.getTransaction(this, id);
    }

    public void addEntry(Entry entry) {
        entries.addEntry(entry);
    }

    private void changeBalance(double amount) {
        balance += amount;
    }

    /**
     * Withdraws money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdraw(double amount) {

        if ((amount > 0) && ((getBalance() - amount) >= 0)) {
            changeBalance(-amount);
            return true;
        }
        changeBalance(-amount);
        return false;
    }

    /**
     * Withdraws cash money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdrawCash(double amount) {
        if ((amount > 0) && ((getBalance() - amount) >= 0)) {
            changeBalance(-amount);
            return true;
        }
        changeBalance(-amount);
        return false;
    }

    /**
     * Adds cash money to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    public boolean addCash(double amount) {
        if (amount > 0) {
            changeBalance(amount);
            return true;
        }
        changeBalance(amount);
        return false;
    }

    /**
     * Adds money to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    public boolean add(double amount) {
        if (amount > 0) {
            changeBalance(amount);
            return true;
        }
        changeBalance(amount);
        return false;
    }

    public Collection<Entry> history(LocalDate from, LocalDate to) {
        return entries.betweenDates(from,to);
    }

    /**
     * Calculates balance on the accounting entries basis
     * @param date
     * @return balance
     */
    public double balanceOn(LocalDate date) {
        double balance = 0;
        for ( Entry e: entries.to(date)) {
            balance += e.getAmount();
        }
        return balance;
    }

    /**
     * Finds the last transaction of the account and rollbacks it
     */
    public Transaction rollbackLastTransaction() {
        return TransactionManager.rollbackTransaction(entries.last().getTransaction(this));
    }

    public double getBalance() {
        return balance;
    }

    public Map<Account, Collection<Entry>> getBeneficiaries() {
        return entries.entriesByBeneficiaries();
    }

    public Collection<Transaction> getPurchases() {
        return entries.getOutcomes().stream().map(e -> e.getTransaction(this)).collect(Collectors.toList());
    }
}
