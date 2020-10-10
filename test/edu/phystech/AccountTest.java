package edu.phystech;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountTest {

    @Test
    public void withdrawTest() {
        DebitCard account = new DebitCard(1);
        double startAmount = 20;
        double withdrawAmount = 10;
        account.add(startAmount);
        double balance = account.getBalance();
        assertTrue(account.withdraw(withdrawAmount));
        assertEquals(account.getBalance(), balance - withdrawAmount);
        double largeAmount = account.getBalance() + 5;
        assertFalse(account.withdraw(largeAmount));
        balance = account.getBalance();
        double negativeAmount = 1;
        assertFalse(account.withdraw(negativeAmount));
        assertEquals(balance - negativeAmount, account.getBalance());
    }

    @Test
    public void addTest() {
        DebitCard account = new DebitCard(1);
        double balance = account.getBalance();
        double negativeAmount = -1;
        assertFalse(account.add(negativeAmount));
        double positiveAmount = 10;
        assertTrue(account.add(positiveAmount));
        assertEquals(balance + positiveAmount + negativeAmount, account.getBalance());
    }
}
