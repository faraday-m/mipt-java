package edu.phystech;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AnalyticsManagerTest {
    private TransactionManager transactionManager;
    private AnalyticsManager analyticsManager;

    @Before
    public void init() {
        transactionManager = new TransactionManager();
        analyticsManager = new AnalyticsManager(transactionManager);
    }

    @Test
    public void getTopTenExpensivePurchases_returnsaAllForOneAccount_whenHasExpensiveOnAnotherAccount() {
        //given
        DebitCard account1 = new DebitCard(1, transactionManager);
        DebitCard account2 = new DebitCard(2, transactionManager);
        account1.addCash(10000);
        account2.addCash(5000);
        Collection<Transaction> transactions = new ArrayList<>();
        transactions.add(transactionManager.createTransaction(1000, account1, account2));
        transactions.add(transactionManager.createTransaction(500, account1, account2));
        transactions.add(transactionManager.createTransaction(800, account1, account2));
        transactions.add(transactionManager.createTransaction(900, account1, account2));
        transactions.add(transactionManager.createTransaction(700, account1, account2));
        transactions.add(transactionManager.createTransaction(600, account1, account2));
        transactions.add(transactionManager.createTransaction(100, account1, account2));
        transactions.add(transactionManager.createTransaction(300, account1, account2));
        transactions.add(transactionManager.createTransaction(50, account1, account2));
        transactions.add(transactionManager.createTransaction(30, account1, account2));
        transactions.add(transactionManager.createTransaction(200, account1, account2));
        transactions.add(transactionManager.createTransaction(400, account1, account2));
        transactions.add(transactionManager.createTransaction(1100, account2, account1)); //from account2

        transactions.forEach(transaction -> transactionManager.executeTransaction(transaction));
        //when
        Collection<Transaction> top10Purchases = analyticsManager.topTenExpensivePurchases(account1);
        //then
        assertTrue(top10Purchases.stream().noneMatch(transaction -> transaction.getOriginator().equals(account2)));
    }


    @Test
    public void getTopTenExpensivePurchases_returnsaTopTenPurchases_whenHasExpensiveOnAnotherAccount() {
        //given
        DebitCard account1 = new DebitCard(1, transactionManager);
        DebitCard account2 = new DebitCard(2, transactionManager);
        account1.addCash(10000);
        account2.addCash(5000);
        Collection<Transaction> transactions = new ArrayList<>();
        transactions.add(transactionManager.createTransaction(1000, account1, account2));
        transactions.add(transactionManager.createTransaction(500, account1, account2));
        transactions.add(transactionManager.createTransaction(800, account1, account2));
        transactions.add(transactionManager.createTransaction(900, account1, account2));
        transactions.add(transactionManager.createTransaction(700, account1, account2));
        transactions.add(transactionManager.createTransaction(600, account1, account2));
        transactions.add(transactionManager.createTransaction(100, account1, account2));
        transactions.add(transactionManager.createTransaction(300, account1, account2));
        transactions.add(transactionManager.createTransaction(50, account1, account2)); //11th
        transactions.add(transactionManager.createTransaction(30, account1, account2)); //12th
        transactions.add(transactionManager.createTransaction(200, account1, account2));
        transactions.add(transactionManager.createTransaction(400, account1, account2));
        transactions.add(transactionManager.createTransaction(1100, account2, account1)); //from account2

        transactions.forEach(transaction -> transactionManager.executeTransaction(transaction));
        //when
        Collection<Transaction> top10Purchases = analyticsManager.topTenExpensivePurchases(account1);
        //then
        assertEquals(top10Purchases.size(), 10);
        Transaction minTransaction = top10Purchases.stream().min(Transaction::compareTo).orElse(null);
        assert minTransaction != null;
        assertEquals(100, minTransaction.getAmount(), 1e-6);
    }


    @Test
    public void getMostFrequentBenefitiary_returnsTopBeneficiary_whenCalled() {
        //given
        DebitCard freqBeneficiary = new DebitCard(1, transactionManager);
        DebitCard otherBeneficiary = new DebitCard(2, transactionManager);
        DebitCard originator = new DebitCard(2, transactionManager);
        originator.addCash(10000);
        freqBeneficiary.addCash(5000);
        otherBeneficiary.addCash(5000);
        Collection<Transaction> transactions = new ArrayList<>();
        transactions.add(transactionManager.createTransaction(1000, originator, freqBeneficiary));
        transactions.add(transactionManager.createTransaction(500, originator, otherBeneficiary));
        transactions.add(transactionManager.createTransaction(800, originator, freqBeneficiary));
        transactions.add(transactionManager.createTransaction(900, originator, otherBeneficiary));
        transactions.add(transactionManager.createTransaction(700, originator, freqBeneficiary));
        transactions.add(transactionManager.createTransaction(600, originator, null));

        transactions.forEach(transaction -> transactionManager.executeTransaction(transaction));
        //when
        Account beneficiary = analyticsManager.mostFrequentBeneficiaryOfAccount(originator);
        //then
        assertEquals(beneficiary, freqBeneficiary);
    }
}
