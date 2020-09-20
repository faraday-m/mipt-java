package edu.phystech;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class CustomerTest {
    @Test
    public void fullNameTest() {
        String name = "John";
        String lastName = "Doe";
        String fullName = "John Doe";
        Customer customer = new Customer(name, lastName);
        assertEquals(customer.fullName(), fullName);
    }

    @Test
    public void openAccountTest() {
        Customer customer = new Customer("John", "Doe");
        assertTrue(customer.openAccount(123));
        assertFalse(customer.openAccount(135));
    }

    @Test
    public void closeAccountTest() {
        Customer customer = new Customer("John", "Doe");
        assertFalse(customer.closeAccount());
        customer.openAccount(123);
        assertTrue(customer.closeAccount());
        assertFalse(customer.closeAccount());
    }

    @Test
    public void addMoneyTest() {
        Customer customer = new Customer("John", "Doe");
        double positiveMoney = 10;
        assertFalse(customer.addMoneyToCurrentAccount(positiveMoney));
        customer.openAccount(123);
        Account testAccount = new Account(145);
        assertEquals(customer.addMoneyToCurrentAccount(positiveMoney), testAccount.add(positiveMoney));
        double negativeMoney = -10;
        assertEquals(customer.addMoneyToCurrentAccount(negativeMoney), testAccount.add(negativeMoney));
    }

    @Test
    public void withdrawMoneyTest() {
        Customer customer = new Customer("John", "Doe");
        double positiveMoney = 10;
        assertFalse(customer.withdrawFromCurrentAccount(positiveMoney));
        customer.openAccount(123);
        Account testAccount = new Account(145);
        assertEquals(customer.withdrawFromCurrentAccount(positiveMoney), testAccount.withdraw(positiveMoney));

        double deposit = positiveMoney + 5;
        customer.addMoneyToCurrentAccount(deposit);
        testAccount.add(deposit);
        assertEquals(customer.withdrawFromCurrentAccount(positiveMoney), testAccount.withdraw(positiveMoney));
        assertEquals(customer.withdrawFromCurrentAccount(positiveMoney), testAccount.withdraw(positiveMoney));

        double negativeMoney = -10;
        assertEquals(customer.withdrawFromCurrentAccount(negativeMoney), testAccount.withdraw(negativeMoney));
    }
}
