package edu.phystech;

import java.time.LocalDate;
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
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(new AbstractMap.SimpleEntry<>(null,0L))
                .getKey();
    }

    public Collection<Transaction> topTenExpensivePurchases(Account account) {
        NavigableSet<Transaction> transactions = (NavigableSet<Transaction>) transactionManager.findAllTransactionsByAccount(account);
        return transactions.descendingSet().stream().limit(10).collect(Collectors.toSet());
    }

    public double overallBalanceOfAccounts(List<Account> accounts) {
        return accounts.stream().map(acc -> acc.balanceOn(LocalDate.now())).reduce(0.0, Double::sum);
    }

    public Set<Long> uniqueKeysOf(List<Account> accounts, KeyExtractor<Long, ? super Account> extractor) {
        return accounts.stream().map(extractor::extract).collect(Collectors.toSet());
    }

    public List<Account> accountsRangeFrom(List<Account> accounts, Account minAccount, Comparator<? super Account> comparator) {
        return accounts.stream().filter(acc -> comparator.compare(acc, minAccount) >= 0).collect(Collectors.toList());
    }

    public Optional<Entry> maxExpenseAmountEntryWithinInterval(List<DebitCard> accounts, LocalDate from, LocalDate to) {
        return accounts.stream()
                .map(a -> a.history(from, to))
                .flatMap(Collection::stream)
                .max(Comparator.comparingDouble((Entry entry) -> Math.abs(entry.getAmount())));
    }
}


