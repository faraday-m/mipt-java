package edu.phystech;

import java.time.LocalDate;
import java.util.*;

/**
 * Collection of entries for the account. Use it to save and get history of payments
 */
public class Entries {
    private final SimpleEntitiesStorage<Entry> entries;
    private Entry lastEntry;
    private Account originator;

    public Entries(Account originator) {
        this.entries = new SimpleEntitiesStorage<>(Object::hashCode);
        this.lastEntry = null;
        this.originator = originator;
    }

    public Entries(Collection<Entry> entries) {
        this.entries = new SimpleEntitiesStorage<>(Object::hashCode);
        this.entries.saveAll(entries);
        if (entries.size() != 0) {
            this.lastEntry = (Entry)entries.toArray()[entries.size() - 1];
        } else {
            this.lastEntry = null;
        }
    }


    public Entries(Collection<Entry> entries, KeyExtractor keyExtractor) {
        this.entries = new SimpleEntitiesStorage<>(keyExtractor);
        this.entries.saveAll(entries);
        if (entries.size() != 0) {
            this.lastEntry = (Entry)entries.toArray()[entries.size() - 1];
        } else {
            this.lastEntry = null;
        }
    }


    void addEntry(Entry entry) {
        this.entries.save(entry);
        lastEntry = entry;
    }

    Collection<Entry> from(LocalDate date) {
        ArrayList<Entry> sortedEntries = (ArrayList<Entry>) entries.findAll();
        sortedEntries.sort(Entry::compareTo);
        int position = Arrays.binarySearch(sortedEntries.toArray(), new Entry(null, 0, 0, date.atTime(0,0,0)));
        if (position < 0) {
            position = -1-position;
        }
        return sortedEntries.subList(position, sortedEntries.size());
    }

    Collection<Entry> to(LocalDate date) {
        ArrayList<Entry> sortedEntries = (ArrayList<Entry>) (entries.findAll());
        sortedEntries.sort(Entry::compareTo);
        int position = Arrays.binarySearch(sortedEntries.toArray(), new Entry(null, 0, 0, date.atTime(23,59,59)));
        if (position < 0) {
            position = -1-position;
        }
        return sortedEntries.subList(0, position);
    }

    Collection<Entry> betweenDates(LocalDate from, LocalDate to) {
        ArrayList<Entry> sortedEntries = new ArrayList<>(entries.findAll());
        sortedEntries.sort(Entry::compareTo);
        int fromPosition = Arrays.binarySearch(sortedEntries.toArray(), new Entry(null, 0, 0, from.atTime(0,0,0)));
        int toPosition = Arrays.binarySearch(sortedEntries.toArray(), new Entry(null, 0, 0, to.atTime(23,59,59)));
        if (fromPosition < 0) {
            fromPosition = -1-fromPosition;
        }        if (toPosition < 0) {
            toPosition = -1-toPosition;
        }
        return sortedEntries.subList(fromPosition, toPosition);
    }

    Map<Account, Collection<Entry>> entriesByBeneficiaries() {
        Map<Account, Collection<Entry>> resultMap = new LinkedHashMap<>();
        for (Entry e : entries.findAll()) {
            if (!resultMap.containsKey(e.getTransaction(originator).getBeneficiary())) {
                resultMap.put(e.getTransaction(originator).getBeneficiary(), new ArrayList<>());
            }
            resultMap.get(e.getTransaction(originator).getBeneficiary()).add(e);
        }
        return resultMap;
    }

    Collection<Entry> getIncomes() {
        Collection<Entry> result = new ArrayList<>();
        for (Entry e : entries.findAll()) {
            if (e.getAmount() > 0) {
                result.add(e);
            }
        }
        return result;
    }

    Collection<Entry> getOutcomes() {
        Collection<Entry> result = new ArrayList<>();
        for (Entry e : entries.findAll()) {
            if (e.getAmount() < 0) {
                result.add(e);
            }
        }
        return result;
    }

    Entry last() {
        return lastEntry;
    }
}