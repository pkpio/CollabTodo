package closure.space.collabtodo.models;

import java.util.List;

/**
 * Created by praveen on 8/5/15.
 */
public class Entry {
    String entryid;
    String entryName;
    List<EntryPriority> priorities;

    public String getEntryid() {
        return entryid;
    }

    public void setEntryid(String entryid) {
        this.entryid = entryid;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public List<EntryPriority> getPriorities() {
        return priorities;
    }

    public void setPriorities(List<EntryPriority> priorities) {
        this.priorities = priorities;
    }
}
