package closure.space.collabtodo.params;

/**
 * Created by praveen on 8/5/15.
 */
public class Local {


    // Umundo specific

    /**
     * Umundo channel name
     */
    public static final String CHANNEL_NAME = "CollabTodo";

    /**
     * Lock string to be used for Multicast locking
     */
    public static final String UMUNDO_MUTLICAST_LOCK = "collabTodoMCLock";

    /**
     * Tag to be used with all messages logged related to umundo
     */
    public static final String UMUNDO_LOG_TAG = "collabTodo-umundo";

    /**
     * Enable this to log all the meta and payload in each received umundo message
     */
    public static final Boolean UMESSAGE_LOGGING = true;

    /**
     * Entry priority Max value
     */
    public static final int ENTRY_PRIORITY_MAX = 5;

    /**
     * Entry priority Min value
     */
    public static final int ENTRY_PRIORITY_MIN = 1;

    /**
     * Entry priority Min value
     */
    public static final int ENTRY_PRIORITY_DEFAULT = 3;

    /**
     * Entry name Min characters
     */
    public static final int ENTRY_NAME_MIN_CHARS = 3;

    /**
     * List name Min characters
     */
    public static final int LIST_NAME_MIN_CHARS = 3;
}
