package closure.space.collabtodo.models;

/**
 * Created by praveen on 8/5/15.
 */
public class EntryPriority {
    String userid;
    int priority;

    /**
     * Get user id
     * @return
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Set userid
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * Get priority of entry
     * @return Integer
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Set Priority of entry
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
