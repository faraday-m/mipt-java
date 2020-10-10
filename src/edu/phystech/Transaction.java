package edu.phystech;

import java.time.LocalDateTime;

public class Transaction implements BankEntity {
    private static long id_sequence = 0;

    private final long id;
    private final double amount;
    private final Account originator;
    private final Account beneficiary;
    private final boolean executed;
    private final boolean rolledBack;

    public long getId() { return id; }
    public double getAmount() {
        return amount;
    }

    public Account getOriginator() {
        return originator;
    }

    public Account getBeneficiary() {
        return beneficiary;
    }

    public boolean isExecuted() {
        return executed;
    }

    public boolean isRolledBack() {
        return rolledBack;
    }

    private Transaction(long id, double amount, Account originator, Account beneficiary, boolean executed, boolean rolledBack) {
        this.id = id;
        this.amount = amount;
        this.originator = originator;
        this.beneficiary = beneficiary;
        this.executed = executed;
        this.rolledBack = rolledBack;
    }

    public Transaction(double amount, Account originator, Account beneficiary) {
        this.id = id_sequence++;
        this.amount = amount;
        this.originator = originator;
        this.beneficiary = beneficiary;
        this.executed = false;
        this.rolledBack = false;
    }

    /**
     * Adding entries to both accounts
     * @throws IllegalStateException when was already executed
     */
    public Transaction execute() {
        if (this.isExecuted()) throw new IllegalStateException("Transaction was already executed");
        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = new Transaction(id, amount, originator, beneficiary, true, false);
        if (originator != null) {
            originator.withdraw(amount);
            originator.addEntry(new Entry(beneficiary, transaction, -amount, now));
        }
        beneficiary.add(amount);
        beneficiary.addEntry(new Entry(originator, transaction, amount, now));
        return transaction;
    }

    /**
     * Removes all entries of current transaction from originator and beneficiary
     * @throws IllegalStateException when was already rolled back
     */
    public Transaction rollback() {
        if (this.isExecuted() == false) throw new IllegalStateException("Cannot rollback transaction that hasn't been executed");
        if (this.isRolledBack()) throw new IllegalStateException("Transaction was already rolled back");
        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = new Transaction(id, amount, originator, beneficiary, true, true);
        if (originator != null) {
            originator.add(amount);
            originator.addEntry(new Entry(beneficiary, transaction, amount, now));
        }
        beneficiary.withdraw(amount); //change to withdrawcash
        beneficiary.addEntry(new Entry(originator, transaction, -amount, now));
        return transaction;
    }

    @Override
    public EntityKey getKey() {
        return new SimpleEntityKey(EntityKey.EntityType.TRANSACTION, id);
    }
}

