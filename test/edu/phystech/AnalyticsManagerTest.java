package edu.phystech;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.Assert.*;

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

    @Test
    public void getOverallAccountBalance_returnsSumOfDebitAndBonusAccounts_whenCalled() {
            //given
            Account beneficiary = new DebitCard(1, transactionManager);
            DebitCard originator = new DebitCard(2, transactionManager, 0.1);
            Account bonusAccount = originator.getBonusAccount();
            originator.addCash(10000);
            originator.withdraw(1000, beneficiary);
            List<Account> accounts = new ArrayList<>();
            accounts.add(beneficiary);
            accounts.add(originator);
            accounts.add(bonusAccount);
            //when
            double overallBalance = analyticsManager.overallBalanceOfAccounts(accounts);
            //then
            assertEquals(10100, overallBalance, 0.0);
    }

    @Test
    public void uniqueKeysOf_returnsSetOfKeys_whenCalled() {
        //given
        DebitCard beneficiary = new DebitCard(1, transactionManager, 0.1);
        Account originator = new DebitCard(2, transactionManager);
        Account bonusAccount = beneficiary.getBonusAccount();
        List<Account> accounts = new ArrayList<>();
        accounts.add(beneficiary);
        accounts.add(originator);
        accounts.add(bonusAccount);
        //when
        Set<Long> uniqueKeys = analyticsManager.uniqueKeysOf(accounts, new SimpleKeyExtractor());
        //then
        assertTrue(uniqueKeys.contains(1L));
        assertTrue(uniqueKeys.contains(2L));
        assertTrue(uniqueKeys.contains(-1L));
    }


    @Test
    public void accountsRangeFrom_returnsAccsWithBalanceGreaterThen5000_whenCalledWithBalanceComparator() {
        //given
        DebitCard acc1 = new DebitCard(1, transactionManager);
        DebitCard acc2 = new DebitCard(1, transactionManager);
        DebitCard acc3 = new DebitCard(1, transactionManager);
        DebitCard acc4 = new DebitCard(1, transactionManager);

        acc1.addCash(10000);
        acc2.addCash(8000);
        acc3.addCash(2000);
        acc4.addCash(5000);

        List<Account> accounts = new ArrayList<>();
        accounts.add(acc1);
        accounts.add(acc2);
        accounts.add(acc3);
        accounts.add(acc4);
        //when
        List<Account> wealthiestAcccounts = analyticsManager.accountsRangeFrom(accounts,
                acc4,
                Comparator.comparingDouble(acc -> acc.balanceOn(LocalDate.now())));
        //then
        assertTrue(wealthiestAcccounts.contains(acc1));
        assertTrue(wealthiestAcccounts.contains(acc2));
        assertFalse(wealthiestAcccounts.contains(acc3));
        assertTrue(wealthiestAcccounts.contains(acc4));
    }
}
