package edu.phystech;

import edu.phystech.entries.Entries;
import edu.phystech.entries.Entry;
import edu.phystech.transactions.Transaction;
import edu.phystech.transactions.TransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public class Account {
    private final long id;
    private final TransactionManager transactionManager;
    private final Entries entries;
    private double balance;

    public Account(long id, TransactionManager transactionManager) {
        this.id = id;
        this.transactionManager = transactionManager;
        this.entries = new Entries();
    }

    public void addEntry(Transaction transaction) {
        if (!transaction.isRolledBack()) {
            if (this.equals(transaction.getOriginator())) {
                entries.addEntry(new Entry(transaction.getBeneficiary(), transaction, -transaction.getAmount(), LocalDateTime.now()));
            } else if (this.equals(transaction.getBeneficiary())) {
                entries.addEntry(new Entry(transaction.getOriginator(), transaction, transaction.getAmount(), LocalDateTime.now()));
            }
        } else {
            if (this.equals(transaction.getOriginator())) {
                entries.addEntry(new Entry(transaction.getBeneficiary(), transaction, transaction.getAmount(), LocalDateTime.now()));
            } else if (this.equals(transaction.getBeneficiary())) {
                entries.addEntry(new Entry(transaction.getOriginator(), transaction, -transaction.getAmount(), LocalDateTime.now()));
            }
        }
    }

    /**
     * Withdraws money from account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (currentBalance - amount) &ge 0,
     * otherwise returns false
     */
    public boolean withdraw(double amount, Account beneficiary) {
        if (amount <= 0 || amount > getBalance()) {
            return false;
        } else {
            Transaction transaction = transactionManager.createTransaction(amount, this, beneficiary);
            transactionManager.executeTransaction(transaction);
            balance -= transaction.getAmount();
            return true;
        }
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
        return withdraw(amount, null);
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
        return add(amount, null);
    }

    /**
     * Adds money to account. <b>Should use TransactionManager to manage transactions</b>
     *
     * @param amount amount of money to add
     * @return true
     * if amount &gt 0,
     * otherwise returns false
     */
    public boolean add(double amount, Account originator) {
        if (amount <= 0) {
            return false;
        } else {
            Transaction transaction = transactionManager.createTransaction(amount, originator, this);
            transactionManager.executeTransaction(transaction);
            balance += transaction.getAmount();
            return true;
        }
    }


    public Collection<Entry> history(LocalDate from, LocalDate to) {
        return entries.betweenDates(from, to);
    }

    /**
     * Calculates balance on the accounting entries basis
     * @param date
     * @return balance
     */
    public double balanceOn(LocalDate date) {
        return entries.betweenDates(LocalDate.MIN, date).stream().map(Entry::getAmount).reduce(0.0, Double::sum);
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Finds the last transaction of the account and rollbacks it
     */
    public void rollbackLastTransaction() {
        transactionManager.rollbackTransaction(entries.last().getTransaction());
    }
}

