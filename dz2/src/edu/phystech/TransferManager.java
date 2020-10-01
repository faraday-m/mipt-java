package edu.phystech;

import java.util.LinkedHashMap;
import java.util.Map;

/** High-level class for managing accounts and transfering money */
public class TransferManager {
    private static long id_sequence = 0;

    private Map<Long, Account> accounts;

    public TransferManager() {
        accounts = new LinkedHashMap<>();
    }

    public long createAccount() {
        id_sequence += 1;
        accounts.put(id_sequence, new Account(id_sequence));
        return id_sequence;
    }

    private Account getAccount(Long id) {
        if (id == null) return null;
        return accounts.get(id);
    }

    public long transferMoney(long originator, long beneficiary, double amount) {
        Transaction transaction = TransactionManager.createTransaction(amount, getAccount(originator), getAccount(beneficiary));
        TransactionManager.executeTransaction(transaction);
        return transaction.getId();
    }

    public long addCash(long beneficiary, double amount) {
        Transaction transaction = TransactionManager.createTransaction(amount, null, getAccount(beneficiary));
        TransactionManager.executeTransaction(transaction);
        return transaction.getId();
    }

    public long withdrawCash(long originator, double amount) {
        Transaction transaction = TransactionManager.createTransaction(amount, getAccount(originator), null);
        TransactionManager.executeTransaction(transaction);
        return transaction.getId();
    }

    public long rollbackLastTransaction(long accountId) {
        Transaction transaction = getAccount(accountId).rollbackLastTransaction();
        return transaction.getId();
    }

    public long rollbackTransaction(Transaction transaction) {
        TransactionManager.rollbackTransaction(transaction);
        return transaction.getId();
    }

    public long rollbackTransaction(long accountId, long transactionId) {
        Transaction transaction = TransactionManager.rollbackTransaction(getTransactionById(accountId, transactionId));
        return transaction.getId();
    }

    public Transaction getTransactionById(Long accountId, long transactionId) {
        return TransactionManager.getTransaction(getAccount(accountId), transactionId);
    }

    public double getAccountBalance(long accountId) {
        return getAccount(accountId).getBalance();
    }
}
