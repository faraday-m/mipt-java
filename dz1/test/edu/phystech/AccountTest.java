package edu.phystech;

import edu.phystech.transactions.TransactionManager;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountTest {
    private TransactionManager transactionManager;

    @Before
    public void init() {
        transactionManager = new TransactionManager();
    }

    @Test
    public void withdrawCashMoney_returnsTrue_whenAmountLessThanBalance() {
        //given
        Account account = new Account(1, transactionManager);
        double startAmount = 20;
        account.addCash(startAmount);
        //when
        double withdrawAmount = 10;
        boolean isSuccess = account.withdrawCash(withdrawAmount);
        //then
        assertTrue(isSuccess);
    }

    @Test
    public void withdrawCashMoney_returnsFalse_whenAmountLessThanZero() {
        //given
        Account account = new Account(1, transactionManager);
        double startAmount = 20;
        account.addCash(startAmount);
        //when
        double withdrawAmount = -1;
        boolean isSuccess = account.withdrawCash(withdrawAmount);
        //then
        assertFalse(isSuccess);
    }

    @Test
    public void withdrawCashMoney_returnsFalse_whenAmountGreaterThanBalance() {
        //given
        Account account = new Account(1, transactionManager);
        double startAmount = 20;
        account.addCash(startAmount);
        //when
        double withdrawAmount = 30;
        boolean isSuccess = account.withdrawCash(withdrawAmount);
        //then
        assertFalse(isSuccess);
    }

    @Test
    public void withdrawCashMoney_balanceChanged_whenAmountLessThanBalance() {
        //given
        Account account = new Account(1, transactionManager);
        double startAmount = 20;
        account.addCash(startAmount);
        //when
        double withdrawAmount = 10;
        account.withdrawCash(withdrawAmount);
        //then
        assertEquals(startAmount - withdrawAmount, account.getBalance());
    }


    @Test
    public void withdrawCashMoney_balanceNotChanged_whenAmountLessThanZero() {
        //given
        Account account = new Account(1, transactionManager);
        double startAmount = 20;
        account.addCash(startAmount);
        //when
        double withdrawAmount = -1;
        account.withdrawCash(withdrawAmount);
        //then
        assertEquals(startAmount, account.getBalance());
    }

    @Test
    public void withdrawCashMoney_balanceNotChanged_whenAmountGreaterThanBalance() {
        //given
        Account account = new Account(1, transactionManager);
        double startAmount = 20;
        account.addCash(startAmount);
        //when
        double withdrawAmount = 30;
        account.withdrawCash(withdrawAmount);
        //then
        assertEquals(startAmount, account.getBalance());
    }

    @Test
    public void getBalance_BalanceEqualsAmount_WhenCalledInitialAdd() {
        //given
        Account account = new Account(1, transactionManager);
        double startAmount = 20;
        //when
        account.addCash(startAmount);
        //then
        assertEquals(startAmount, account.getBalance());
    }

    @Test
    public void addCashMoney_returnsFalse_whenAmountLessThanZero() {
        //given
        Account account = new Account(1, transactionManager);
        //when
        double negativeAmount = -1;
        boolean isSuccess = account.addCash(negativeAmount);
        //then
        assertFalse(isSuccess);
    }

    @Test
    public void addCashMoney_balanceNotChanged_whenAmountLessThanZero() {
        //given
        Account account = new Account(1, transactionManager);
        double balance = account.getBalance();
        //when
        double negativeAmount = -1;
        account.addCash(negativeAmount);
        //then
        assertEquals(balance, account.getBalance());
    }

    @Test
    public void addCashMoney_returnsTrue_whenAmountGreaterThenZero() {
        //given
        Account account = new Account(1, transactionManager);
        //when
        double positiveAmount = 10;
        boolean isSuccess = account.addCash(positiveAmount);
        //then
        assertTrue(isSuccess);
    }

    @Test
    public void addCashMoney_balanceChanged_whenAmountGreaterThanZero() {
        //given
        Account account = new Account(1, transactionManager);
        double balance = account.getBalance();
        //when
        double positiveAmount = 10;
        account.addCash(positiveAmount);
        //then
        assertEquals(balance + positiveAmount, account.getBalance());
    }

    @Test
    public void withdrawMoneyFromAccount_balanceOfOriginatorDecreased_whenAmountGreaterThanZero() {
        //given
        Account account = new Account(1, transactionManager);
        Account account2 = new Account(2, transactionManager);
        double positiveAmount = 10;
        account.addCash(positiveAmount);
        double balance = account.getBalance();
        //when
        account.withdraw(positiveAmount, account2);
        //then
        assertEquals(balance - positiveAmount, account.getBalance());
    }

    @Test
    public void withdrawMoneyFromAccount_balanceOfBeneficiaryIncreased_whenAmountGreaterThanZero() {
        //given
        Account account = new Account(1, transactionManager);
        Account account2 = new Account(2, transactionManager);
        double balance = account2.getBalance();
        double positiveAmount = 10;
        account.addCash(positiveAmount);
        //when
        account.withdraw(positiveAmount, account2);
        //then
        assertEquals(balance + positiveAmount, account2.getBalance());
    }

    @Test
    public void addMoneyToAccount_balanceOfOriginatorDecreased_whenAmountGreaterThanZero() {
        //given
        Account account = new Account(1, transactionManager);
        Account account2 = new Account(2, transactionManager);
        double positiveAmount = 10;
        account.addCash(positiveAmount);
        double balance = account.getBalance();
        //when
        account2.add(positiveAmount, account);
        //then
        assertEquals(balance - positiveAmount, account.getBalance());
    }

    @Test
    public void addMoneyToAccount_balanceOfBeneficiaryIncreased_whenAmountGreaterThanZero() {
        //given
        Account account = new Account(1, transactionManager);
        Account account2 = new Account(2, transactionManager);
        double balance = account2.getBalance();
        double positiveAmount = 10;
        account.addCash(positiveAmount);
        //when
        account2.add(positiveAmount, account);
        //then
        assertEquals(balance + positiveAmount, account2.getBalance());
    }
}
