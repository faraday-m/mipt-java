package edu.phystech;

import edu.phystech.entries.Entry;
import edu.phystech.transactions.Transaction;
import edu.phystech.transactions.TransactionManager;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnalyticsManager {
    private final TransactionManager transactionManager;

    public AnalyticsManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Account mostFrequentBeneficiaryOfAccount(Account account) {
        Collection<Transaction> transactions = transactionManager.findAllTransactionsByAccount(account);
        return transactions.stream()
                .map(Transaction::getBeneficiary)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(new AbstractMap.SimpleEntry<>(null,0L))
                .getKey();
    }

    public Collection<Transaction> topTenExpensivePurchases(Account account) {
        SortedSet<Transaction> transactions = (SortedSet<Transaction>) transactionManager.findAllTransactionsByAccount(account);
        return transactions.stream().limit(10).collect(Collectors.toSet());
    }
}


