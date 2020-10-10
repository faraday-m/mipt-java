package edu.phystech;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsManager {

    public Account mostFrequentBeneficiaryOfAccount(DebitCard account) {
        Account maxBeneficiary = null;
        int numOfTransactions = 0;
        for (Account acc : account.getBeneficiaries().keySet()) {
            if (account.getBeneficiaries().get(acc).size() > numOfTransactions) {
                numOfTransactions = account.getBeneficiaries().get(acc).size();
                maxBeneficiary = acc;
            }
        }
        return maxBeneficiary;
    }

    public Collection<Transaction> topTenExpensivePurchases(DebitCard account) {
        List<Transaction> sortedTransactions = new ArrayList<>(account.getPurchases());
        sortedTransactions.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return Double.compare(o1.getAmount(), o2.getAmount());
            }
        });
        if (sortedTransactions.size() < 10) {
            return sortedTransactions;
        }
        return  sortedTransactions.subList(0,10);
    }


    public double overallBalanceOfAccounts(List<Account> accounts) {
        double balance = 0;
        for (Account account : accounts) {
            balance += account.balanceOn(LocalDate.now());
        }
        return balance;
    }

    public Set<Object> uniqueKeysOf(List<Account> accounts, KeyExtractor extractor) {
        return accounts.stream().map(extractor::extract).collect(Collectors.toSet());
    }

    public List<Account> accountsRangeFrom(List<Account> accounts, Account minAccount, Comparator<Account> comparator) {
        return accounts.stream().filter((Account a1) -> comparator.compare(a1, minAccount) > 0)
                .collect(Collectors.toList());
    }

}


