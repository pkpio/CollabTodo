package closure.space.collabtodo.database;

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

        // Handle saving of each priority of this event
        
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

    }
}
