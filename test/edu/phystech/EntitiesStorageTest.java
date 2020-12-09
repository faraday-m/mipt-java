package edu.phystech;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EntitiesStorageTest {

    @Test
    public void save_successfulRetrieving_whenAddRightEntity() {
        //given
        Account account = new DebitCard(1L, new TransactionManager());
        BankEntitiesStorage<Long, Account> storage = new SimpleEntitiesStorage<>(new SimpleKeyExtractor());
        storage.save(account);
        //when
        Account foundAcc = storage.findByKey(1L);
        //then
        assertEquals(foundAcc, account);
    }

    @Test(expected = NullPointerException.class)
    public void save_throwsNPE_whenAddNullEntity() {
        //given
        Account account = null;
        BankEntitiesStorage<Long, Account> storage = new SimpleEntitiesStorage<>(new SimpleKeyExtractor());
        //when
        storage.save(account);
        //then
        fail();
    }

    @Test
    public void findAll_returnsAll_whenAddDebitAndBonus() {
        //given
        List<Account> accounts = new ArrayList<>();
        TransactionManager tm = new TransactionManager();
        Account debit = new DebitCard(1L, tm);
        Account debit2 = new DebitCard(2L, tm);
        Account bonus = new BonusAccount(-1L, 0.2);
        accounts.add(debit);
        accounts.add(debit2);
        accounts.add(bonus);
        BankEntitiesStorage<Long, Account> storage = new SimpleEntitiesStorage<>(new SimpleKeyExtractor());
        storage.saveAll(accounts);
        //when
        List<Account> results = storage.findAll();
        //then
        assertTrue(results.contains(debit));
        assertTrue(results.contains(debit2));
        assertTrue(results.contains(bonus));
        assertEquals(3, results.size());
    }

    @Test
    public void deleteByKey_returnsAllExceptDeleted_whenDeleteDebitWithId2() {
        //given
        List<Account> accounts = new ArrayList<>();
        TransactionManager tm = new TransactionManager();
        Account debit = new DebitCard(1L, tm);
        Account debit2 = new DebitCard(2L, tm);
        Account bonus = new BonusAccount(-1L, 0.2);
        accounts.add(debit);
        accounts.add(debit2);
        accounts.add(bonus);
        BankEntitiesStorage<Long, Account> storage = new SimpleEntitiesStorage<>(new SimpleKeyExtractor());
        storage.saveAll(accounts);
        //when
        storage.deleteByKey(2L);
        //then
        assertNull(storage.findByKey(2L));
        List<Account> results = storage.findAll();
        assertTrue(results.contains(debit));
        assertTrue(results.contains(bonus));
        assertEquals(2, results.size());
    }


    @Test
    public void deleteAll_returnsListWithBonus_whenDeleteAllDebits() {
        //given
        List<Account> accounts = new ArrayList<>();
        TransactionManager tm = new TransactionManager();
        Account debit = new DebitCard(1L, tm);
        Account debit2 = new DebitCard(2L, tm);
        Account bonus = new BonusAccount(-1L, 0.2);
        accounts.add(debit);
        accounts.add(debit2);
        accounts.add(bonus);
        BankEntitiesStorage<Long, Account> storage = new SimpleEntitiesStorage<>(new SimpleKeyExtractor());
        storage.saveAll(accounts);
        List<Account> accountsToDelete = new ArrayList<>();
        accountsToDelete.add(debit);
        accountsToDelete.add(debit2);
        //when
        storage.deleteAll(accountsToDelete);
        //then
        List<Account> results = storage.findAll();
        assertEquals(1, results.size());
        assertEquals(results.get(0), bonus);
    }

}
