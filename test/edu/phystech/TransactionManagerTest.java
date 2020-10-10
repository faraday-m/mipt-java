package edu.phystech;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.*;

public class TransactionManagerTest {

    @Test
    public void executeTransactionTest() {
        DebitCard account = new DebitCard(1);
        DebitCard account2 = new DebitCard(2);
        Transaction transaction = TransactionManager.createTransaction(100, account, account2);
        Transaction newTransaction = TransactionManager.executeTransaction(transaction);
        assertEquals(transaction.getId(), newTransaction.getId());
        assertEquals(transaction.getAmount(), newTransaction.getAmount());
        assertEquals(transaction.getBeneficiary(), newTransaction.getBeneficiary());
        assertEquals(transaction.getOriginator(), newTransaction.getOriginator());
        assertTrue(newTransaction.isExecuted());
        assertFalse(transaction.isExecuted());

        assertEquals(newTransaction, new ArrayList<>(TransactionManager.findAllTransactionsByAccount(account)).get(0));
    }


    @Test
    public void rollbackTransactionTest() {
        DebitCard account = new DebitCard(1);
        DebitCard account2 = new DebitCard(2);
        Transaction transaction = TransactionManager.createTransaction(100, account, account2);
        Transaction newTransaction = TransactionManager.executeTransaction(transaction);
        Transaction rolledTransaction = TransactionManager.rollbackTransaction(newTransaction);
        assertEquals(transaction.getId(), rolledTransaction.getId());
        assertEquals(transaction.getAmount(), rolledTransaction.getAmount());
        assertEquals(transaction.getBeneficiary(), rolledTransaction.getBeneficiary());
        assertEquals(transaction.getOriginator(), rolledTransaction.getOriginator());
        assertEquals(newTransaction.isExecuted(), rolledTransaction.isExecuted());
        assertFalse(newTransaction.isRolledBack());
        assertTrue(rolledTransaction.isRolledBack());
        TransactionManager.findAllTransactionsByAccount(account).stream().forEach(t -> System.out.println(t.getId()));
        assertEquals(rolledTransaction, new ArrayList<>(TransactionManager.findAllTransactionsByAccount(account)).get(0));
    }


    @Test(expected = IllegalStateException.class)
    public void executeExecutedTransactionTest() {
        DebitCard account = new DebitCard(1);
        DebitCard account2 = new DebitCard(2);
        Transaction transaction = TransactionManager.createTransaction(100, account, account2);
        Transaction newTransaction = TransactionManager.executeTransaction(transaction);
        TransactionManager.executeTransaction(newTransaction);
    }

    @Test(expected = IllegalStateException.class)
    public void rollbackRolledTransactionTest() {
        DebitCard account = new DebitCard(1);
        DebitCard account2 = new DebitCard(2);
        Transaction transaction = TransactionManager.createTransaction(100, account, account2);
        Transaction newTransaction = TransactionManager.executeTransaction(transaction);
        Transaction rolledTransaction = TransactionManager.rollbackTransaction(newTransaction);
        TransactionManager.rollbackTransaction(rolledTransaction);
    }

    @Test(expected = IllegalStateException.class)
    public void rollbackNotExecutedTransactionTest() {
        DebitCard account = new DebitCard(1);
        DebitCard account2 = new DebitCard(2);
        Transaction transaction = TransactionManager.createTransaction(100, account, account2);
        TransactionManager.rollbackTransaction(transaction);
    }
}
