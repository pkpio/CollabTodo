package closure.space.collabtodo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by praveen on 8/5/15.
 */
public class EntryPriority {
    @SerializedName("userid")
    String userid;

    @SerializedName("priority")
    int priority;

    // The entry to which this priority belongs
    @SerializedName("entryid")
    String entryid;

    /**
     * Get user id
     *
     * @return
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Set userid
     *
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * Get global unique entryid to which this priority belongs
     *
     * @return entryid
     */
    public String getEntryid() {
        return entryid;
    }

    /**
     * Set global unique entryid to which this priority belongs
     *
     * @param entryid
     */
    public void setEntryid(String entryid) {
        this.entryid = entryid;
    }

    /**
     * Get priority of entry
     *
     * @return Integer
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Set Priority of entry
     *
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
}