package closure.space.collabtodo.database;

import java.util.List;

import closure.space.collabtodo.models.Entry;

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
    public static void save(Entry entry) {
        // Check if it's database already
        Entry dbEntry = EntryDao.getEntry(entry.getEntryid());

        // Update if exists
        if (dbEntry != null)
            entry.setId(dbEntry.getId()); // These are sugar id
        entry.save();
    }

    /**
     * Deletes the Entry from the local database.
     *
     * @param entryid
     */
    public static void delete(String entryid) {
        Entry.deleteAll(Entry.class, "entryid = ?", entryid);
    }

    /**
     * Fetches the Entry from the local database
     *
     * @param entryid Entryid of the Entry in local database
     * @return Entry with given id
     */
    public static Entry getEntry(String entryid) {
        List<Entry> entries = Entry.find(Entry.class, "entryid = ?", entryid);
        if (entries == null || entries.size() == 0)
            return null;
        else
            return entries.get(0);
    }

    /**
     * Fetches all Entries in a list from local database
     *
     * @param listid TodoListId of the list
     * @return List of Entries
     */
    public static List<Entry> getEntries(String listid) {
        return Entry.find(Entry.class, "listid = ?", listid);
    }

}
