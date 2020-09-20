package edu.phystech;

public class Account {
    private final long id;
    private double balance;

    public Account(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    /**
     * Withdraws money from account balance
     *
     * @param amount amount of money to withdraw
     * @return true
     * if amount &gt 0 and (balance - amount) &gt 0,
     * otherwise returns false
     */
    public boolean withdraw(double amount) {
        if ((amount > 0) && ((balance - amount)>=0)) {
            balance -= amount;
            return true;
        }
        return false;
    }

    /**
     * Adds money to account balance
     *
     * @param amount amount of money to add on account
     * @return true if amount &gt 0, otherwise returns false
     */
    public boolean add(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }
}
