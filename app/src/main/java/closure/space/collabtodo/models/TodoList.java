package closure.space.collabtodo.models;

import java.util.List;

/**
 * Created by praveen on 8/5/15.
 */
public class TodoList {
    String listid;
    String listname;
    List<Entry> entries;

    /**
     * Get global unique id of this list
     *
     * @return id
     */
    public String getListid() {
        return listid;
    }

    /**
     * Set a global unique id of this list
     *
     * @param listid
     */
    public void setListid(String listid) {
        this.listid = listid;
    }

    /**
     * Get user readable list name
     *
     * @return
     */
    public String getListname() {
        return listname;
    }

    /**
     * Set a user readable list name
     *
     * @param listname
     */
    public void setListname(String listname) {
        this.listname = listname;
    }

    /**
     * Get list of entries in this todolist
     *
     * @return
     */
    public List<Entry> getEntries() {
        return entries;
    }

    /**
     * Set list of entries in this todolist
     *
     * @param entries
     */
    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }
}
