package edu.phystech;

import edu.phystech.transactions.TransactionManager;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class CustomerTest {
    private TransactionManager transactionManager;

    @Before
    public void init() {
        transactionManager = new TransactionManager();
    }

    @Test
    public void setName_nameAndSurnameEqualsToConcatenated_WhenCustomerCreated() {
        //given
        String name = "John";
        String lastName = "Doe";
        String fullName = "John Doe";
        //when
        Customer customer = new Customer(name, lastName, transactionManager);
        //then
        assertEquals(customer.fullName(), fullName);
    }

    @Test
    public void openAccount_returnsTrue_WhenOpeningFirstAccount() {
        //given
        Customer customer = new Customer("John", "Doe", transactionManager);
        //when
        boolean gotFirstAccount = customer.openAccount(135);
        //then
        assertTrue(gotFirstAccount);
    }

    @Test
    public void openAccount_returnsFalse_WhenOpeningSecondAccount() {
        //given
        Customer customer = new Customer("John", "Doe", transactionManager);
        customer.openAccount(123);
        //when
        boolean gotSecondAccount = customer.openAccount(135);
        //then
        assertFalse(gotSecondAccount);
    }

    @Test
    public void closeAccount_returnsTrue_WhenCloseExistingAccount() {
        //given
        Customer customer = new Customer("John", "Doe", transactionManager);
        customer.openAccount(123);
        //when
        boolean hasClosedAccount = customer.closeAccount();
        //then
        assertTrue(hasClosedAccount);
    }

    @Test
    public void closeAccount_returnsFalse_WhenCloseNonExistingAccount() {
        //given
        Customer customer = new Customer("John", "Doe", transactionManager);
        //when
        boolean hasClosedAccount = customer.closeAccount();
        //then
        assertFalse(hasClosedAccount);
    }


    @Test
    public void closeAccount_returnsFalse_WhenCloseAlreadyClosedAccount() {
        //given
        Customer customer = new Customer("John", "Doe", transactionManager);
        customer.openAccount(123);
        //when
        customer.closeAccount();
        boolean hasClosedAccount = customer.closeAccount();
        //then
        assertFalse(hasClosedAccount);
    }

    @Test
    public void addMoney_returnsFalse_whenAddToNonExistingAccount() {
        //given
        Customer customer = new Customer("John", "Doe", transactionManager);
        double positiveMoney = 10;
        //when
        boolean gotMoney = customer.addMoneyToCurrentAccount(positiveMoney);
        //then
        assertFalse(gotMoney);
    }

    @Test
    public void addMoney_returnsTrue_whenAddPositiveNumberToExistingAccount() {
        //given
        Customer customer = new Customer("John", "Doe", transactionManager);
        double positiveMoney = 10;
        customer.openAccount(123);
        //when
        boolean gotMoney = customer.addMoneyToCurrentAccount(positiveMoney);
        //then
        assertTrue(gotMoney);
    }

    @Test
    public void addMoney_returnsFalse_whenAddNegativeNumberToExistingAccount() {
        //given
        Customer customer = new Customer("John", "Doe", transactionManager);
        double negativeMoney = -10;
        customer.openAccount(123);
        //when
        boolean gotMoney = customer.addMoneyToCurrentAccount(negativeMoney);
        //then
        assertFalse(gotMoney);
    }

    @Test
    public void withdrawMoney_returnsFalse_whenWithdrawFromNonExistingAccount() {
        //given
        Customer customer = new Customer("John", "Doe", transactionManager);
        double positiveMoney = 10;
        //when
        boolean withdrawedMoney = customer.withdrawFromCurrentAccount(positiveMoney);
        //then
        assertFalse(withdrawedMoney);
    }

    @Test
    public void withdrawMoney_returnsTrue_whenWithdrawFromExistingAccountLessThanBalance() {
        //given
        Customer customer = new Customer("John", "Doe", transactionManager);
        double positiveMoney = 10;
        double deposit = 15;
        customer.openAccount(123);
        customer.addMoneyToCurrentAccount(deposit);
        //when
        boolean withdrawedMoney = customer.withdrawFromCurrentAccount(positiveMoney);
        //then
        assertTrue(withdrawedMoney);
    }


    @Test
    public void withdrawMoney_returnsFalse_whenWithdrawFromExistingAccountGreaterThanBalance() {
        //given
        Customer customer = new Customer("John", "Doe", transactionManager);
        double positiveMoney = 10;
        double deposit = 5;
        customer.openAccount(123);
        customer.addMoneyToCurrentAccount(deposit);
        //when
        boolean withdrawedMoney = customer.withdrawFromCurrentAccount(positiveMoney);
        //then
        assertFalse(withdrawedMoney);
    }


    @Test
    public void withdrawMoney_returnsFalse_whenWithdrawNegativeAmount() {
        //given
        Customer customer = new Customer("John", "Doe", transactionManager);
        double negativeMoney = -1;
        double deposit = 5;
        customer.openAccount(123);
        customer.addMoneyToCurrentAccount(deposit);
        //when
        boolean withdrawedMoney = customer.withdrawFromCurrentAccount(negativeMoney);
        //then
        assertFalse(withdrawedMoney);
    }
}
