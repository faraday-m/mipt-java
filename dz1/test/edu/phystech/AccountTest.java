package edu.phystech;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountTest {
    @Test
    public void withdrawMoney_returnsTrue_whenAmountLessThanBalance() {
        //given
        Account account = new Account(1);
        double startAmount = 20;
        account.add(startAmount);
        //when
        double withdrawAmount = 10;
        boolean isSuccess = account.withdraw(withdrawAmount);
        //then
        assertTrue(isSuccess);
    }

    @Test
    public void withdrawMoney_returnsFalse_whenAmountLessThanZero() {
        //given
        Account account = new Account(1);
        double startAmount = 20;
        account.add(startAmount);
        //when
        double withdrawAmount = -1;
        boolean isSuccess = account.withdraw(withdrawAmount);
        //then
        assertFalse(isSuccess);
    }

    @Test
    public void withdrawMoney_returnsFalse_whenAmountGreaterThanBalance() {
        //given
        Account account = new Account(1);
        double startAmount = 20;
        account.add(startAmount);
        //when
        double withdrawAmount = 30;
        boolean isSuccess = account.withdraw(withdrawAmount);
        //then
        assertFalse(isSuccess);
    }

    @Test
    public void withdrawMoney_balanceChanged_whenAmountLessThanBalance() {
        //given
        Account account = new Account(1);
        double startAmount = 20;
        account.add(startAmount);
        //when
        double withdrawAmount = 10;
        account.withdraw(withdrawAmount);
        //then
        assertEquals(startAmount - withdrawAmount, account.getBalance());
    }


    @Test
    public void withdrawMoney_balanceNotChanged_whenAmountLessThanZero() {
        //given
        Account account = new Account(1);
        double startAmount = 20;
        account.add(startAmount);
        //when
        double withdrawAmount = -1;
        account.withdraw(withdrawAmount);
        //then
        assertEquals(startAmount, account.getBalance());
    }

    @Test
    public void withdrawMoney_balanceNotChanged_whenAmountGreaterThanBalance() {
        //given
        Account account = new Account(1);
        double startAmount = 20;
        account.add(startAmount);
        //when
        double withdrawAmount = 30;
        account.withdraw(withdrawAmount);
        //then
        assertEquals(startAmount, account.getBalance());
    }

    @Test
    public void getBalance_BalanceEqualsAmount_WhenCalledInitialAdd() {
        //given
        Account account = new Account(1);
        double startAmount = 20;
        //when
        account.add(startAmount);
        //then
        assertEquals(startAmount, account.getBalance());
    }

    @Test
    public void addMoney_returnsFalse_whenAmountLessThanZero() {
        //given
        Account account = new Account(1);
        //when
        double negativeAmount = -1;
        boolean isSuccess = account.add(negativeAmount);
        //then
        assertFalse(isSuccess);
    }

    @Test
    public void addMoney_balanceNotChanged_whenAmountLessThanZero() {
        //given
        Account account = new Account(1);
        double balance = account.getBalance();
        //when
        double negativeAmount = -1;
        account.add(negativeAmount);
        //then
        assertEquals(balance, account.getBalance());
    }

    @Test
    public void addMoney_returnsTrue_whenAmountGreaterThenZero() {
        //given
        Account account = new Account(1);
        //when
        double positiveAmount = 10;
        boolean isSuccess = account.add(positiveAmount);
        //then
        assertTrue(isSuccess);
    }

    @Test
    public void addMoney_balanceChanged_whenAmountGreaterThanZero() {
        //given
        Account account = new Account(1);
        double balance = account.getBalance();
        //when
        double positiveAmount = 10;
        account.add(positiveAmount);
        //then
        assertEquals(balance + positiveAmount, account.getBalance());
    }
}
