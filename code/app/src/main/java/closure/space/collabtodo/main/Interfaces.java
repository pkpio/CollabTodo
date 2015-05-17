package closure.space.collabtodo.main;

import java.util.List;

import closure.space.collabtodo.models.Entry;

/**
 * Various interfaces used in the application
 * <p/>
 * Created by praveen on 9/5/15.
 */
public class Interfaces {

    /**
     * An interface to update & exchange entries
     */
    public interface EntryUpdater {

        /**
         * Update the current list of entries shown on the UI
         *
         * @param listid   id of the list to use
         * @param listname name of the list, for title bar
         */
        public void updateEntryList(String listid, String listname);
    }

    /**
     * An interface to update & exchange todolists list
     */
    public interface TodoListUpdater {

        /**
         * Update the current list of todolist shown on the UI
         */
        public void updateTodoList();

    }

    /**
     * A handler to update the current UI
     */
    public interface UIUpdater {

        /**
         * Update the current UI
         */
        public void updateUI();

    }

    /**
     * An interface to update the state of left navigation drawer
     */
    public interface DrawerStateUpdater {

        /**
         * Set navigation drawers state
         *
         * @param state True: open. False: close
         * @param title Title of the activity
         */
        public void setDrawerState(Boolean state, String title);
    }

}
