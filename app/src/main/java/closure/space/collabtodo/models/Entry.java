package closure.space.collabtodo.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;
import java.util.List;

import closure.space.collabtodo.params.Local;

/**
 * Created by praveen on 8/5/15.
 */
public class Entry extends SugarRecord<Entry> {

    @SerializedName("entryid")
    String entryid;

    @SerializedName("entryname")
    String entryName;

    @SerializedName("entrydone")
    Boolean entryDone;

    @SerializedName("todolistid")
    String listid;

    public Entry() {
        this.entryDone = false;
    }

    /**
     * New entry
     *
     * @param entryid
     * @param entryName
     * @param listid
     */
    public Entry(String entryid, String entryName, String listid) {
        this.entryid = entryid;
        this.entryName = entryName;
        this.entryDone = false;
        this.listid = listid;
    }

    /**
     * Sugar doesn't support saving of Lists into database. So, we add it to ignore
     * list and handle them in EntryDao save methods appropriately.
     */
    @SerializedName("priorities")
    @Ignore
    List<EntryPriority> priorities;

    /**
     * Get global unique of the entry
     *
     * @return entryid
     */
    public String getEntryid() {
        return entryid;
    }

    /**
     * Set global unique of the entry
     *
     * @param entryid
     */
    public void setEntryid(String entryid) {
        this.entryid = entryid;
    }

    /**
     * Get readable name of the entry
     *
     * @return
     */
    public String getEntryName() {
        return entryName;
    }

    /**
     * Set readable name of the entry
     *
     * @param entryName
     */
    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    /**
     * Get Entry done status
     *
     * @return status
     */
    public Boolean isEntryDone() {
        return entryDone;
    }

    /**
     * Set Entry done status
     *
     * @param entryDone True => Done
     */
    public void setEntryDone(Boolean entryDone) {
        this.entryDone = entryDone;
    }

    /**
     * Get list of priorities given to the event
     *
     * @return
     */
    public List<EntryPriority> getPriorities() {
        return priorities;
    }

    /**
     * Set list of priorities given to the event
     *
     * @param priorities
     */
    public void setPriorities(List<EntryPriority> priorities) {
        this.priorities = priorities;
    }


    /**
     * Get the listid of the TodoList to which this event belongs to
     *
     * @return TodoList id
     */
    public String getListid() {
        return listid;
    }

    /**
     * Set the listid of the TodoList to which this event belongs to
     *
     * @param listid TodoList id
     */
    public void setListid(String listid) {
        this.listid = listid;
    }

    /**
     * Get the priority set by a particular user
     *
     * @param userId User id
     * @return Priority given by this user
     */
    public int getPriority(String userId) {
        if (priorities == null)
            return Local.ENTRY_PRIORITY_DEFAULT;

        // We need search if this user has already added  a priority entry and if so, update it
        int userPrio = Local.ENTRY_PRIORITY_DEFAULT;
        for (EntryPriority mEP : priorities)
            if (mEP.getUserid().contentEquals(userId))
                userPrio = mEP.getPriority();
        return userPrio;
    }

    /**
     * Updates the priority given by a user for this entry
     *
     * @param priority Priority value
     * @param userId   Userid of the user - this is currently the unique device id
     */
    public void updatePriority(String userId, int priority) {
        if (priorities == null)
            priorities = new ArrayList<EntryPriority>();

        // We need search if this user has already added  a priority entry and if so, update it
        Boolean found = false;
        for (EntryPriority mEP : priorities)
            if (mEP.getUserid().contentEquals(userId)) {
                mEP.setPriority(priority);
                found = true;
            }

        // Not in list? Add an entryPrio for this user
        if (!found) priorities.add(new EntryPriority(userId, priority, entryid));
    }

    /**
     * Average of all user priorities for this entry
     *
     * @return mean of all priorities
     */
    public int getMeanPriority() {
        if (priorities == null || priorities.size() == 0)
            return 0;

        int total = 0;
        for (int i = 0; i < priorities.size(); i++)
            total += priorities.get(i).getPriority();

        return total / priorities.size();
    }
}
