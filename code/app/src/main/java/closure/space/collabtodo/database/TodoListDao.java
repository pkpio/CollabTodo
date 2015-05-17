package closure.space.collabtodo.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import closure.space.collabtodo.models.Entry;
import closure.space.collabtodo.models.TodoList;

/**
 * TodoList Data Access Object
 * <p/>
 * This abstracts the details of reading or updating TodoList entries in / out of the Sugar Database
 * <p/>
 * Created by praveen on 8/5/15.
 */
public class TodoListDao {

    /**
     * Saves / updates the current todolist in local database.
     * Creates a new todolist if doesn't exist already.
     *
     * @param list TodoList
     */
    public static void save(TodoList list) {
        list.save();

        // Set listids for each entry
        if (list.getEntries() == null)
            return;
        for (Entry entry : list.getEntries()) {
            entry.setListid(list.getListid());
            EntryDao.save(entry);
        }
    }


    /**
     * Deletes the TodoList and all it's entries from the local database.
     *
     * @param list TodoList
     */
    public static void delete(TodoList list) {
        if (list == null)
            return;

        // Delete all entries of a list
        if (list.getEntries() != null)
            for (Entry entry : list.getEntries())
                EntryDao.delete(entry.getEntryid());

        // Delete the list
        TodoList.deleteAll(TodoList.class, "listid = ?", list.getListid());
    }

    /**
     * Deletes the TodoList and all it's entries from the local database.
     *
     * @param listid TodoList id
     */
    public static void delete(String listid) {
        delete(getList(listid));
    }

    /**
     * Get a list of all TodoLists in the local database
     *
     * @return List of TodoList
     */
    public static List<TodoList> list() {
        List<TodoList> mTodoLists = TodoList.listAll(TodoList.class);
        for (TodoList list : mTodoLists)
            list.setEntries(EntryDao.getEntries(list.getListid()));
        return mTodoLists;
    }

    public static TodoList getList(String listid) {
        List<TodoList> lists = TodoList.find(TodoList.class, "listid = ?", listid);
        if (lists == null || lists.size() == 0)
            return null;
        lists.get(0).setEntries(EntryDao.getEntries(lists.get(0).getListid()));
        return lists.get(0);
    }

}
