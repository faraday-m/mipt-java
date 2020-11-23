package edu.phystech.transactions;

import edu.phystech.Account;
import edu.phystech.entries.Entry;

public class Transaction implements Comparable {
    private final long id;
    private final double amount;
    private final Account originator;
    private final Account beneficiary;
    private boolean executed;
    private boolean rolledBack;

    public Transaction(long id, double amount, Account originator, Account beneficiary, boolean executed, boolean rolledBack) {
        this.id = id;
        this.amount = amount;
        this.originator = originator;
        this.beneficiary = beneficiary;
        this.executed = executed;
        this.rolledBack = rolledBack;
    }

    public double getAmount() {
        return amount;
    }

    public Account getOriginator() {
        return originator;
    }

    public Account getBeneficiary() {
        return beneficiary;
    }

    public boolean isRolledBack() {
        return rolledBack;
    }

    /**
     * Adding entries to both accounts
     * @throws IllegalStateException when was already executed
     */
    public Transaction execute() {
        if (this.executed) {
            throw new IllegalStateException("Transaction is already executed");
        }
        if (this.originator != null) {
            this.originator.addEntry(this);
        }
        if (this.beneficiary != null) {
            this.beneficiary.addEntry(this);
        }
        this.executed = true;
        return this;
    }

    /**
     * Removes all entries of current transaction from originator and beneficiary
     * @throws IllegalStateException when was already rolled back
     */
    public Transaction rollback() {
        if (this.rolledBack) {
            throw new IllegalStateException("Transaction is already rolled back");
        }
        this.rolledBack = true;
        if (this.originator != null) {
            this.originator.addEntry(this);
        }
        if (this.beneficiary != null) {
            this.beneficiary.addEntry(this);
        }
        return this;
    }

    @Override
    public int compareTo(Object o) {
        if (this.equals(o)) {
            return 0;
        }
        if (!(o instanceof Transaction)) {
            throw new ClassCastException(o.getClass().toString());
        } else {
            Transaction other = (Transaction) o;
            return Double.compare(this.amount, other.amount);
        }
    }
}


