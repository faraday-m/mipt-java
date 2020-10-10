package edu.phystech;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static junit.framework.TestCase.*;

public class EntriesTest {

    private TransactionManager transactionManager = new TransactionManager();

    private Collection<Entry> generateEntries() {
        Entry entry1 = new Entry(null, 0, 5, LocalDateTime.of(1980,01,01,01,01));
        Entry entry2 = new Entry(null, 0, -5, LocalDateTime.of(1990,01,01,00,00));
        Entry entry3 = new Entry(null, 0, 10, LocalDateTime.of(2000,01,01,01,01));
        return Arrays.asList(entry1, entry2, entry3);
    }

    @Test
    public void fromTest() {
        Entries entries = new Entries(generateEntries());
        LocalDate lowDate = LocalDate.of(1990,01,01);
        Collection<Entry> entriesFrom = entries.from(lowDate);
        System.out.println(entriesFrom.stream().map(entry -> entry.getTime()).collect(Collectors.toList()));
        for (Entry e : entriesFrom) {
            assertFalse(e.getTime().isBefore(LocalDateTime.of(lowDate, LocalTime.of(0,0))));
        }
    }



    @Test
    public void toTest() {
        Entries entries = new Entries(generateEntries());
        LocalDate upDate = LocalDate.of(1990,01,01);
        Collection<Entry> entriesTo = entries.to(upDate);
        for (Entry e : entriesTo) {
            assertFalse(e.getTime().isAfter(LocalDateTime.of(upDate, LocalTime.of(0,0))));
        }
    }

    @Test
    public void betweenTest() {
        Entries entries = new Entries(generateEntries());
        LocalDate lowDate = LocalDate.of(1985,01,01);
        LocalDate upDate = LocalDate.of(1995, 01, 01);
        Collection<Entry> entriesBetween = entries.betweenDates(lowDate, upDate);
        for (Entry e : entriesBetween) {
            assertFalse(e.getTime().isAfter(LocalDateTime.of(upDate, LocalTime.of(0,0))));
            assertFalse(e.getTime().isBefore(LocalDateTime.of(lowDate, LocalTime.of(0,0))));
        }
    }

    @Test
    public void outcomesTest() {
        Entries entries = new Entries(generateEntries());
        Collection<Entry> outcomes = entries.getOutcomes();
        for (Entry e : outcomes) {
            assertTrue(e.getAmount() < 0);
        }
    }

    @Test
    public void incomesTest() {
        Entries entries = new Entries(generateEntries());
        Collection<Entry> incomes = entries.getIncomes();
        for (Entry e : incomes) {
            assertTrue(e.getAmount() > 0);
        }
    }

    @Test
    public void lastTest() {
        Entries entries = new Entries((Account) null);
        Collection<Entry> generatedEntries = generateEntries();
        for (Entry e : generatedEntries) {
            entries.addEntry(e);
            assertEquals(entries.last(), e);
        }
    }
}
