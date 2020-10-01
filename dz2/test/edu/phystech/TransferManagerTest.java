package edu.phystech;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TransferManagerTest {

    @Test
    public void transferMoneyTest() {
        TransferManager transferManager = new TransferManager();
        long account1 = transferManager.createAccount();
        long account2 = transferManager.createAccount();
        double amount1 = 200;
        double amount2 = 300;

        transferManager.addCash(account1, amount1);
        transferManager.addCash(account2, amount2);
        assertEquals(transferManager.getAccountBalance(account1), amount1);
        assertEquals(transferManager.getAccountBalance(account2), amount2);
        double transferAmount = 150;
        transferManager.transferMoney(account2, account1, transferAmount);
        assertEquals(transferManager.getAccountBalance(account1), amount1 + transferAmount);
        assertEquals(transferManager.getAccountBalance(account2), amount2 - transferAmount);
    }

    @Test
    public void rollbackTransactionTest() {
        TransferManager transferManager = new TransferManager();
        long account = transferManager.createAccount();
        long account2 = transferManager.createAccount();
        double amount = 100;
        transferManager.transferMoney(account, account2, amount);
        assertEquals(-amount, transferManager.getAccountBalance(account));
        assertEquals(amount, transferManager.getAccountBalance(account2));
        long transactionId = transferManager.rollbackLastTransaction(account);
        Transaction transaction = transferManager.getTransactionById(account, transactionId);
        assertTrue(transaction.isRolledBack());
        assertEquals(0.0, transferManager.getAccountBalance(account));
        assertEquals(0.0, transferManager.getAccountBalance(account2));
    }
}
