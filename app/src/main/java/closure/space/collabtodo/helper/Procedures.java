package closure.space.collabtodo.helper;

import org.umundo.core.Message;

import closure.space.collabtodo.database.EntryDao;
import closure.space.collabtodo.database.TodoListDao;
import closure.space.collabtodo.helper.NumberFactory;
import closure.space.collabtodo.main.ApplicationClass;
import closure.space.collabtodo.models.Entry;
import closure.space.collabtodo.models.TodoList;
import closure.space.collabtodo.params.Global;

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
         * @param list List object to create in database
         * @return Newly created TodoList object
         */
        public static TodoList createList(TodoList list) {
            // Save it locally
            TodoListDao.save(list);

            // Transfer this asynchronously into network
            Message msg = new Message();
            msg.putMeta(Global.UMESSAGE_KEY_METHOD, String.valueOf(Global.METHOD_CREATE_LIST));
            msg.setData(JsonFactory.toJson(list).getBytes());
            ApplicationClass.send(msg);

            return list;
        }

        /**
         * Deletes a list and all it's entries locally and notifies all nodes in network
         *
         * @param listid id of the TodoList to be deleted
         */
        public static void deleteList(String listid) {
            // Delete it locally
            TodoListDao.delete(listid);

            // Transfer this asynchronously into network
            Message msg = new Message();
            msg.putMeta(Global.UMESSAGE_KEY_METHOD, String.valueOf(Global.METHOD_DELETE_LIST));
            msg.putMeta(Global.UMESSAGE_KEY_ID, listid);
            ApplicationClass.send(msg);
        }

        /**
         * Creates / updates a new entry locally and notifies all nodes in network
         *
         * @return Created / Updated Entry
         */
        public static Entry updateEntry(Entry entry) {
            // Save it locally
            EntryDao.save(entry);

            // Transfer this asynchronously into network
            Message msg = new Message();
            msg.putMeta(Global.UMESSAGE_KEY_METHOD, String.valueOf(Global.METHOD_UPDATE_ENTRY));
            msg.setData(JsonFactory.toJson(entry).getBytes());
            ApplicationClass.send(msg);

            return entry;
        }

        /**
         * Deletes an entry locally and notifies all nodes in network
         *
         * @param entryid entryid of the entry to be deleted
         */
        public static void deleteEntry(String entryid) {
            // Delete it locally
            EntryDao.delete(entryid);

            // Transfer this asynchronously into network
            Message msg = new Message();
            msg.putMeta(Global.UMESSAGE_KEY_METHOD, String.valueOf(Global.METHOD_DELETE_ENTRY));
            msg.putMeta(Global.UMESSAGE_KEY_ID, entryid);
            ApplicationClass.send(msg);
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
            // Save it locally
            TodoListDao.save(list);

            // -TODO- Trigger UI refresh
        }

        /**
         * Deletes a list locally and notifies the UI
         *
         * @param listid id of the TodoList to be deleted
         */
        public static void deleteList(String listid) {
            // Delete it locally
            TodoListDao.delete(listid);

            // -TODO- Trigger UI refresh
        }

        /**
         * Creates / Updates an entry locally and notifies the UI
         *
         * @param entry Entry object to be created or updated
         */
        public static void updateEntry(Entry entry) {
            // Save it locally
            EntryDao.save(entry);

            // -TODO- Trigger UI refresh
        }

        /**
         * Deletes an entry locally and notifies the UI
         *
         * @param entryid id of the Entry to be deleted
         */
        public static void deleteEntry(String entryid) {
            // Delete it locally
            EntryDao.delete(entryid);

            // -TODO- Trigger UI refresh
        }
    }
}
