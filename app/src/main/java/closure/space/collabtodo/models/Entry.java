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

    @SerializedName("entrypriority")
    int entryPriority;

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
     * Get priority of the entry
     *
     * @return priority
     */
    public int getEntryPriority() {
        return entryPriority;
    }

    /**
     * Set entry priority
     *
     * @param entryPriority priority value
     */
    public void setEntryPriority(int entryPriority) {
        this.entryPriority = entryPriority;
    }
}
