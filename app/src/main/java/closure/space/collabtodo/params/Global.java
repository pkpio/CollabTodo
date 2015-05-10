package closure.space.collabtodo.params;

/**
 * <p/>
 * These are constants across all devices and the umundo transfer protocol
 * <p/>
 * Created by praveen on 8/5/15.
 */
public class Global {
    public static final int METHOD_CREATE_LIST = 1;
    public static final int METHOD_UPDATE_ENTRY = 2;
    public static final int METHOD_DELETE_LIST = 3;
    public static final int METHOD_DELETE_ENTRY = 4;

    /**
     * Umundo Message Meta key used to store the remote method to be executed
     */
    public static final String UMESSAGE_KEY_METHOD = "CTRemoteMethod";

    /**
     * Umundo Message Meta key used to store the id required for remote method invocation
     * <p/>
     * Delete methods just need an id - entryId or listId
     */
    public static final String UMESSAGE_KEY_ID = "CTRemoteId";

}
