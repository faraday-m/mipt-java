package edu.phystech;

import java.time.LocalDateTime;

/**
 * The record of allocating the amount to the account
 * Amount can be either positive or negative depending on originator or beneficiary
 */
public class Entry implements Comparable {
    private final Account account; // other side of transaction
    private final Transaction transaction;
    private final double amount; // less than 0 if account is beneficiary of transaction
    private final LocalDateTime time;

    public Entry(Account account, Transaction transaction, double amount, LocalDateTime time) {
        this.account = account;
        this.transaction = transaction;
        this.amount = amount;
        this.time = time;
    }

    @Override
    public int compareTo(Object o) {
        if (this.equals(o)) {
            return 0;
        }
        if (!(o instanceof Entry)) {
            throw new ClassCastException(o.getClass().toString());
        } else {
            Entry other = (Entry) o;
            return this.time.compareTo(other.time);
        }
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public double getAmount() {
        return amount;
    }

}



