package closure.space.collabtodo.helper;

import closure.space.collabtodo.database.TodoListDao;
import closure.space.collabtodo.helper.NumberFactory;
import closure.space.collabtodo.models.Entry;
import closure.space.collabtodo.models.TodoList;

/**
 * These are exhaustive list of procedures that are supported
 * either locally or in the backend network.
 * <p/>
 * Created by praveen on 9/5/15.
 */
public class Procedures {

    /**
     * These are list of procedures that are called when user performs an action.
     * All procedures are designed to take care of transferring the respective changes
     * into the backend network and thus syncing with other clients
     */
    public static class Local {

        /**
         * Creates a list from it's name and notifies all nodes in network
         *
         * @param listName
         * @return Newly created TodoList object
         */
        public static TodoList createList(String listName) {

            // Create a new TodoList locally
            TodoList nTodoList = new TodoList();
            nTodoList.setListname(listName);
            // -TODO- id should be prepended by userid
            nTodoList.setListid(String.valueOf(NumberFactory.uniqueLocal()));

            // Save it locally
            TodoListDao.save(nTodoList);

            // Transfer this asynchronously into network
            // -TODO- Implement this

            return nTodoList;
        }

        /**
         * Deletes a list and all it's entries locally and notifies all nodes in network
         *
         * @param listid id of the TodoList to be deleted
         */
        public static void deleteList(String listid) {
            // -TODO- Implement
        }

        /**
         * Creates / updates a new entry locally and notifies all nodes in network
         *
         * @return Created / Updated Entry
         */
        public static Entry updateEntry(Entry entry) {
            // -TODO- Implement completely
            entry.save();
            return entry;
        }

        /**
         * Deletes an entry locally and notifies all nodes in network
         *
         * @param entryid entryid of the entry to be deleted
         */
        public static void deleteEntry(String entryid) {
            // -TODO- Implement
        }
    }

    /**
     * These are list of procedures that are used when changes occur at some node in network.
     * All procedures are designed to take care of updating changes locally and notifying the
     * current UI about any refreshes.
     */
    public static class Remote {

        /**
         * Creates a new list locally and notifies the UI
         *
         * @param list TodoList object to be created
         */
        public static void createList(TodoList list) {
            // -TODO- Implement
        }

        /**
         * Deletes a list locally and notifies the UI
         *
         * @param listid id of the TodoList to be deleted
         */
        public static void deleteList(String listid) {
            // -TODO- Implement
        }

        /**
         * Creates / Updates an entry locally and notifies the UI
         *
         * @param entry Entry object to be created or updated
         */
        public static void updateEntry(Entry entry) {
            // -TODO- Implement
        }

        /**
         * Deletes an entry locally and notifies the UI
         *
         * @param entryid id of the Entry to be deleted
         */
        public static void deleteEntry(String entryid) {
            // -TODO- Implement
        }
    }
}
