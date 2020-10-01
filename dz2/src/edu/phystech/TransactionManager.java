package edu.phystech;

import java.time.LocalDate;
import java.util.*;

/**
 * Manages all transactions within the application
 */
public class TransactionManager {
    public static class TransactionBuilder {
        private double amount;
        private Account originator;
        private Account beneficiary;


        public TransactionBuilder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder withOriginator(Account originator) {
            this.originator = originator;
            return this;
        }

        public TransactionBuilder withBeneficiary(Account beneficiary) {
            this.beneficiary = beneficiary;
            return this;
        }

        public Transaction build() {
            return new Transaction(amount, originator, beneficiary);
        }
    }

    private static Map<Account, Map<Long, Transaction>> transactions = new LinkedHashMap<>();
    /**
     * Creates and stores transactions
     * @param amount
     * @param originator
     * @param beneficiary
     * @return created Transaction
     */
    public static Transaction createTransaction(double amount,
                                         Account originator,
                                         Account beneficiary) {
        TransactionBuilder transactionBuilder = new TransactionBuilder()
                .withAmount(amount)
                .withOriginator(originator)
                .withBeneficiary(beneficiary);
        return transactionBuilder.build();
    }

    public static Transaction getTransaction(Account account, long id) {
        return transactions.get(account).get(id);
    }

    public static Collection<Transaction> findAllTransactionsByAccount(Account account) {
        return transactions.getOrDefault(account, new LinkedHashMap<>()).values();
    }

    public static Transaction rollbackTransaction(Transaction transaction) {
        Transaction rolledTransaction = transaction.rollback();
        addTransactionToMap(rolledTransaction.getOriginator(), rolledTransaction);
        return rolledTransaction;
    }

    public static Transaction executeTransaction(Transaction transaction) {
        Transaction executedTransacton = transaction.execute();
        addTransactionToMap(executedTransacton.getOriginator(), executedTransacton);
        return executedTransacton;
    }

    private static void addTransactionToMap(Account account, Transaction transaction) {
        transactions.computeIfAbsent(account, k -> new HashMap<>());
        transactions.get(account).put(transaction.getId(), transaction);
    }

}

