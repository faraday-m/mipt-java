package edu.phystech;

import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsManager {

    public Account mostFrequentBeneficiaryOfAccount(Account account) {
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

    public Collection<Transaction> topTenExpensivePurchases(Account account) {
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
}


