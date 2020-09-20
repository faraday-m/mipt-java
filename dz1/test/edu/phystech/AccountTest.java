package edu.phystech;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountTest {

    @Test
    public void withdrawTest() {
        Account account = new Account(1);
        double startAmount = 20;
        double withdrawAmount = 10;
        account.add(startAmount);
        double balance = account.getBalance();
        assertTrue(account.withdraw(withdrawAmount));
        assertEquals(account.getBalance(), balance - withdrawAmount);
        double largeAmount = account.getBalance() + 5;
        assertFalse(account.withdraw(largeAmount));
        balance = account.getBalance();
        double negativeAmount = -1;
        assertFalse(account.withdraw(negativeAmount));
        assertEquals(account.getBalance(), balance);
    }

    @Test
    public void addTest() {
        Account account = new Account(1);
        double balance = account.getBalance();
        double negativeAmount = -1;
        assertFalse(account.add(negativeAmount));
        assertEquals(balance, account.getBalance());
        double positiveAmount = 10;
        assertTrue(account.add(positiveAmount));
        assertEquals(balance + positiveAmount, account.getBalance());
    }
}
