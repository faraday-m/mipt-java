package edu.phystech;

import java.time.LocalDate;
import java.util.Collection;

public class BonusAccount implements Account {
    private static long sequenceId = -1;
    private final Entries entries;
    private final double cashback;
    private double balance = 0;
    private long id;
    public BonusAccount(double cashback) {
        this.cashback = cashback;
        this.entries = new Entries(this);
        this.id = sequenceId--;
    }

    public double getCashback(){
        return cashback;
    }

    @Override
    public void addEntry(Entry entry) {
        entries.addEntry(entry);
    }

    @Override
    public double balanceOn(LocalDate date) {
        double balance = 0;
        for ( Entry e: entries.to(date)) {
            balance += e.getAmount();
        }
        return balance;
    }

    @Override
    public boolean add(double amount) {
        if (amount > 0) {
            changeBalance(amount * getCashback());
            TransactionManager.createTransaction(amount * getCashback(), null, this).execute();
            return true;
        }
        changeBalance(amount * getCashback());
        return false;
    }

    @Override
    public boolean withdraw(double amount) {
        if ((amount > 0) && ((balance - amount) >= 0)) {
            changeBalance(-amount);
            return true;
        }
        changeBalance(-amount);
        return false;
    }

    public Collection<Entry> history(LocalDate from, LocalDate to) {
        return entries.betweenDates(from,to);
    }

    public double getBalance() {
        return balance;
    }


    private void changeBalance(double amount) {
        balance += amount;
    }

    @Override
    public EntityKey getKey() {
        return new SimpleEntityKey(EntityKey.EntityType.ACCOUNT, this.id);
    }
}
