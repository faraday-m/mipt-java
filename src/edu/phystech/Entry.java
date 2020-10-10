package edu.phystech;

import java.time.LocalDateTime;

/**
 * The record of allocating the amount to the account
 * Amount can be either positive or negative depending on originator or beneficiary
 */
public class Entry implements Comparable, BankEntity {
    private static int idSequence = 0;
    private final Account account;
    private final long transactionId;
    private final double amount;
    private final LocalDateTime time;
    private final long id;

    public Entry(Account account, Transaction transaction, double amount, LocalDateTime time) {
        this.account = account;
        this.transactionId = transaction.getId();
        this.amount = amount;
        this.time = time;
        this.id = idSequence++;
    }
    public Entry(Account account, long transactionId, double amount, LocalDateTime time) {
        this.account = account;
        this.transactionId = transactionId;
        this.amount = amount;
        this.time = time;
        this.id = idSequence++;
    }

    public Account getAccount() {
        return account;
    }

    public Transaction getTransaction(Account originator) {
        return TransactionManager.getTransaction(originator, transactionId);
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Entry) {
            return this.getTime().compareTo(((Entry) o).getTime());
        }
        throw new RuntimeException(o.getClass() + " is not an instance of Entry");
    }

    @Override
    public EntityKey getKey() {
        return new SimpleEntityKey(EntityKey.EntityType.ENTRY, id);
    }
}



