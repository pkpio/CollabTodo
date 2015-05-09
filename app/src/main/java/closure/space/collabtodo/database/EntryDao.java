package closure.space.collabtodo.database;

import java.util.List;

import closure.space.collabtodo.models.Entry;
import closure.space.collabtodo.models.TodoList;

/**
 * Entry Data Access Object
 * <p/>
 * This abstracts the details of reading or updating Entry in / out of the Sugar Database
 * <p/>
 * Created by praveen on 8/5/15.
 */
public class EntryDao {

    /**
     * Saves / updates the current entry in local database.
     * Creates a new entry if doesn't exist already.
     *
     * @param entry
     */
    public void save(Entry entry) {
        entry.save();
        // -TODO- Handle saving of each EntryPriority of this event
    }


    /**
     * Deletes the Entry from the local database.
     *
     * @param entry
     */
    public void delete(Entry entry) {
        delete(entry.getEntryid());
    }

    /**
     * Deletes the Entry from the local database.
     *
     * @param entryid
     */
    public void delete(String entryid) {
        // -TODO- Implement this
    }

    /**
     * Fetches the Entry from the local database
     *
     * @param entryid Entryid of the Entry in local database
     * @return Entry with given id
     */
    public Entry getEntry(String entryid) {
        List<Entry> entries = Entry.find(Entry.class, "entryid = ?", entryid);
        if (entries == null || entries.size() == 0)
            return null;
        else
            return setEntryPriorities(entries.get(0));
    }

    /**
     * Fetches all Entries in a list from local database
     *
     * @param listid TodoListId of the list
     * @return List of Entries
     */
    public List<Entry> getEntries(String listid) {
        return setEntryPriorities(Entry.find(Entry.class, "listid = ?", listid));
    }

    /**
     * Sets all entry priorities corresponding to this event from the local Database.
     * Since Sugar doesn't support saving or retrieving of Lists, we keep a pointer,
     * entryid, in each EntryPriority and save, retrieve them accordingly.
     */
    private Entry setEntryPriorities(Entry entry) {
        // -TODO- Retrieve them from EntryPriorityDao and set
        return entry;
    }

    /**
     * Sets all entry priorities corresponding to each event in this List from the local Database.
     * Since Sugar doesn't support saving or retrieving of Lists, we keep a pointer,
     * entryid, in each EntryPriority and save, retrieve them accordingly.
     */
    private List<Entry> setEntryPriorities(List<Entry> entries) {
        for (Entry entry : entries)
            entry = setEntryPriorities(entry);
        return entries;
    }

}
