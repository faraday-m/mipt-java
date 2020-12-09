package edu.phystech;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BonusAccount implements Account {
    private double cashbackQuotient;
    private Entries entries;
    private long id;

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public double getCashbackQuotient() {
        return cashbackQuotient;
    }

    private final TransactionManager transactionManager;

    public BonusAccount(long id, double cashbackQuotient) {
        this.cashbackQuotient = cashbackQuotient;
        this.entries = new Entries();
        this.transactionManager = new TransactionManager();
        this.id = id;
    }

    @Override
    public void addEntry(Transaction transaction) {
        LocalDateTime dateTime = LocalDateTime.now();
        if (entries.last() != null && !dateTime.isAfter(entries.last().getTime())) {
            dateTime = entries.last().getTime().plusNanos(1L);
        }
        if (!transaction.isRolledBack()) {
            entries.addEntry(new Entry(this, transaction, transaction.getAmount(), dateTime));
        } else {
            entries.addEntry(new Entry(this, transaction, -transaction.getAmount(), dateTime));
        }
    }

    @Override
    public double balanceOn(LocalDate date) {
        return entries.betweenDates(LocalDate.MIN, date).stream().map(Entry::getAmount).reduce(0.0, Double::sum);
    }

    @Override
    public long getId() {
        return id;
    }
}
