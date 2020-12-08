package edu.phystech;

import java.util.*;

/**
 * Manages all transactions within the application
 */
public class TransactionManager {
    private Map<Account, NavigableSet<Transaction>> transactionMap;
    private int id_sequence;

    public TransactionManager() {
        this.transactionMap = new LinkedHashMap<>();
        id_sequence = 0;
    }

    /**
     * Creates and stores transactions
     *
     * @param amount
     * @param originator
     * @param beneficiary
     * @return created Transaction
     */
    public Transaction createTransaction(double amount,
                                         Account originator,
                                         Account beneficiary) {
        Transaction transaction = new Transaction(++id_sequence, amount, originator, beneficiary, false, false);
        if (!transactionMap.containsKey(originator)) {
            transactionMap.put(originator, new TreeSet<>());
        }
        transactionMap.get(originator).add(transaction);
        return transaction;
    }

    public Collection<Transaction> findAllTransactionsByAccount(Account account) {
        if (!transactionMap.containsKey(account)) {
            transactionMap.put(account, new TreeSet<>());
        }
        return transactionMap.get(account);
    }

    public void rollbackTransaction(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }

    public void executeTransaction(Transaction transaction) {
        if (transaction != null) {
            transaction.execute();
        }
    }
}

