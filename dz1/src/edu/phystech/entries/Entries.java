package edu.phystech.entries;

import edu.phystech.transactions.Transaction;

import java.time.LocalDate;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Collection of entries for the account. Use it to save and get history of payments
 */
public class Entries {
    private final SortedSet<Entry> entries;

    public Entries() {
        this.entries = new TreeSet<>();
    }

    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public Collection<Entry> from(LocalDate date) {
        Entry mockEntry = new Entry(null, null, 0, date.atStartOfDay());
        return entries.tailSet(mockEntry);
    }

    public Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        Entry mockBeginEntry = new Entry(null, null, 0, from.atStartOfDay());
        Entry mockEndEntry = new Entry(null, null, 0, to.atStartOfDay().plusDays(1));
        return entries.subSet(mockBeginEntry, mockEndEntry);
    }

    public Entry last() {
        return entries.last();
    }
}

