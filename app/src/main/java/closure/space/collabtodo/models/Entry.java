package closure.space.collabtodo.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.List;

/**
 * Created by praveen on 8/5/15.
 */
public class Entry extends SugarRecord<Entry> {
    @SerializedName("entryid")
    String entryid;

    @SerializedName("entryname")
    String entryName;

    /**
     * Sugar doesn't support saving of Lists into database. So, we add it to ignore
     * list and handle them in EntryDao save methods accordingly.
     */
    @SerializedName("priorities")
    @Ignore
    List<EntryPriority> priorities;

    /**
     * Get global unique of the entry
     *
     * @return
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
}
