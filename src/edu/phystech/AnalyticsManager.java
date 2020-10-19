package edu.phystech;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.stream.Collectors;

public class AnalyticsManager {

    public Optional<Account> mostFrequentBeneficiaryOfAccount(DebitCard account) {
        Optional<Account> maxBeneficiary;
        maxBeneficiary = account.getBeneficiaries().entrySet()
                .stream()
                .max(Comparator.comparingInt(e -> account.getBeneficiaries().get(e).size()))
                .map(e -> e.getKey());
        return maxBeneficiary;
    }

    public Collection<Transaction> topTenExpensivePurchases(DebitCard account) {
        return account.getPurchases()
                .stream()
                .sorted(Comparator.comparingDouble(Transaction::getAmount))
                .limit(10)
                .collect(Collectors.toList());
    }


    public double overallBalanceOfAccounts(List<Account> accounts) {
        LocalDate now = LocalDate.now();
        DoubleAccumulator accumulator = new DoubleAccumulator(Double::sum, 0.0);
        accounts.stream().map(a -> a.balanceOn(now))
                .forEach(accumulator::accumulate);
        return accumulator.get();
    }

    public Set<Object> uniqueKeysOf(List<Account> accounts, KeyExtractor extractor) {
        return accounts.stream()
                .map(extractor::extract)
                .collect(Collectors.toSet());
    }

    public List<Account> accountsRangeFrom(List<Account> accounts, Account minAccount, Comparator<Account> comparator) {
        return accounts
                .stream()
                .filter((Account a1) -> comparator.compare(a1, minAccount) > 0)
                .collect(Collectors.toList());
    }

    public Optional<Entry> maxExpenseAmountEntryWithinInterval(List<Account> accounts, LocalDate from, LocalDate to) {
        return accounts.stream()
                .map(a -> a.history(from,to))
                .flatMap(Collection::stream)
                .filter(entry -> entry.getAmount() < 0)
                .min(Comparator.comparingDouble((Entry entry) -> Math.abs(entry.getAmount())));

    }
}


