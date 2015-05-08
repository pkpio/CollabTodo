package closure.space.collabtodo.database;

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
     * @param list
     */
    public void save(TodoList list) {

    }


    /**
     * Deletes the TodoList and all it's entries from the local database.
     *
     * @param list
     */
    public void delete(TodoList list) {
        delete(list.getListid());
    }

    /**
     * Deletes the TodoList and all it's entries from the local database.
     *
     * @param listid
     */
    public void delete(String listid) {

    }

}
