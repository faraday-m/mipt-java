package edu.phystech;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TransactionTest {
    private TransactionManager transactionManager;

    @Before
    public void init() {
        transactionManager = new TransactionManager();
    }

    @Test(expected=IllegalStateException.class)
    public void executeTransaction_throwsIllegalStateEx_whenAlreadyExecuted() {
        //given
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(1, transactionManager);
        account1.addCash(10);
        Transaction transaction = transactionManager.createTransaction(10, account1, account2);
        transaction.execute();
        //when
        transaction.execute();
        //then
        //if exception were not thrown
        fail();
    }

    @Test
    public void executeTransaction_accountsEntryGotTransaction_whenExecuted() {
        //given
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(1, transactionManager);
        account1.addCash(10);
        Transaction transaction = transactionManager.createTransaction(10, account1, account2);
        //when
        transaction.execute();
        //then
        assertEquals(account1.lastEntry().getTransaction(), transaction);
        assertEquals(account2.lastEntry().getTransaction(), transaction);
    }

    @Test
    public void executeTransaction_transactionStatusChangedToExecuted_whenExecuted() {
        //given
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(1, transactionManager);
        account1.addCash(10);
        Transaction transaction = transactionManager.createTransaction(10, account1, account2);
        //when
        Transaction executedTransaction = transaction.execute();
        //then
        assertEquals(executedTransaction, transaction);
        assertTrue(executedTransaction.isExecuted());
    }


    @Test(expected=IllegalStateException.class)
    public void rollbackTransaction_throwsIllegalStateEx_whenAlreadyRolled() {
        //given
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(1, transactionManager);
        account1.addCash(10);
        Transaction transaction = transactionManager.createTransaction(10, account1, account2);
        transaction.execute();
        //when
        transaction.rollback();
        transaction.rollback();
        //then
        //if exception were not thrown
        fail();
    }

    @Test(expected=IllegalStateException.class)
    public void rollbackTransaction_throwsIllegalStateEx_whenNotExecuted() {
        //given
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(1, transactionManager);
        account1.addCash(10);
        Transaction transaction = transactionManager.createTransaction(10, account1, account2);
        //when
        transaction.rollback();
        //then
        //if exception were not thrown
        fail();
    }

    @Test
    public void rollbackTransaction_accountsBalanceReturnsInitial_whenRolledBack() {
        //given
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(1, transactionManager);
        account1.addCash(10);
        double originatorBalance = account1.getBalance();
        double beneficiaryBalance = account2.getBalance();
        Transaction transaction = transactionManager.createTransaction(10, account1, account2);
        transaction.execute();
        //when
        transaction.rollback();
        //then
        assertEquals(account1.getBalance(), originatorBalance);
        assertEquals(account2.getBalance(), beneficiaryBalance);
    }

    @Test
    public void rollbackTransaction_transactionStatusChangedToRolledBack_whenRolledBack() {
        //given
        Account account1 = new Account(1, transactionManager);
        Account account2 = new Account(1, transactionManager);
        account1.addCash(10);
        Transaction transaction = transactionManager.createTransaction(10, account1, account2);
        transaction.execute();
        //when
        Transaction rolledTransaction = transaction.rollback();
        //then
        assertEquals(rolledTransaction, transaction);
        assertTrue(rolledTransaction.isRolledBack());
    }
}
