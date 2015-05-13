package closure.space.collabtodo.main;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.orm.SugarApp;

import org.umundo.core.Discovery;
import org.umundo.core.Message;
import org.umundo.core.Node;
import org.umundo.core.Publisher;
import org.umundo.core.Receiver;
import org.umundo.core.Subscriber;

import closure.space.collabtodo.helper.DeviceUuidFactory;
import closure.space.collabtodo.helper.JsonFactory;
import closure.space.collabtodo.helper.Procedures;
import closure.space.collabtodo.models.Entry;
import closure.space.collabtodo.models.TodoList;
import closure.space.collabtodo.params.Global;
import closure.space.collabtodo.params.Local;
import closure.space.collabtodo.testsuite.EntryTest;

/**
 * Created by praveen on 9/5/15.
 */
public class ApplicationClass extends SugarApp {
    Discovery mDiscovery;
    WifiManager.MulticastLock mMulticastLock;

    static Node mNode;
    static Publisher mPublisher;
    static Subscriber mSubscriber;

    // Prefix unique id that any class my use
    static String mUUID;

    Thread testPublishing;


    public ApplicationClass() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Init uMundo
        initUmundo();
    }

    /**
     * Initialize umundo backend.
     * Usually this is done when application starts and there is no reason to do it again
     * unless you know what you are doing.
     */
    public void initUmundo() {

        // Set the unique device id
        DeviceUuidFactory mDUF = new DeviceUuidFactory(this);
        this.mUUID = mDUF.getDeviceUuid().toString();

        // Get a lock on WifiMulticast
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            mMulticastLock = wifi.createMulticastLock(Local.UMUNDO_MUTLICAST_LOCK);
            mMulticastLock.acquire();
        } else {
            Log.v(Local.UMUNDO_LOG_TAG, "Cannot get WifiManager");
        }

        // System.loadLibrary("umundoNativeJava");
        System.loadLibrary("umundoNativeJava_d");

        // Setup node and it's discovery
        mDiscovery = new Discovery(Discovery.DiscoveryType.MDNS);
        mNode = new Node();
        mDiscovery.add(mNode);

        // Init publishers and subscribers
        mPublisher = new Publisher(Local.CHANNEL_NAME);
        mSubscriber = new Subscriber(Local.CHANNEL_NAME);
        mSubscriber.setReceiver(new CustomUmunodReceiver());

        // Add pubs and subs on node
        mNode.addPublisher(mPublisher);
        mNode.addSubscriber(mSubscriber);

        // Testing
        testPublishing = new Thread(new TestPublishing());
        testPublishing.start();
    }

    /**
     * A custom umundo receiver to handle all Subscribed traffic
     */
    public class CustomUmunodReceiver extends Receiver {

        /**
         * Called by umundo core when ever a new message arrives for a subscriber
         *
         * @param msg Umundo Message received
         */
        public void receive(Message msg) {

            // Log messages received, if enabled
            if (Local.UMESSAGE_LOGGING) {
                for (String key : msg.getMeta().keySet())
                    Log.i(Local.UMUNDO_LOG_TAG, key + ": " + msg.getMeta(key));
                Log.i(Local.UMUNDO_LOG_TAG, "payload: " + new String(msg.getData()));
            }

            // Check the method to be executed
            int method = Integer.valueOf(msg.getMeta(Global.UMESSAGE_KEY_METHOD));

            switch (method) {
                case Global.METHOD_CREATE_LIST:
                    Procedures.Remote.createList(JsonFactory.toObject(
                            new String(msg.getData()),
                            TodoList.class));
                    break;
                case Global.METHOD_DELETE_LIST:
                    Procedures.Remote.deleteList(msg.getMeta(Global.UMESSAGE_KEY_ID));
                    break;
                case Global.METHOD_UPDATE_ENTRY:
                    Procedures.Remote.updateEntry(JsonFactory.toObject(
                            new String(msg.getData()),
                            Entry.class));
                    break;
                case Global.METHOD_DELETE_ENTRY:
                    Procedures.Remote.deleteEntry(msg.getMeta(Global.UMESSAGE_KEY_ID));
                    break;

            }
        }
    }

    /**
     * Send data to other devices in the network
     *
     * @param msg Message to be sent
     */
    public static void send(Message msg) {
        mPublisher.send(msg);
    }

    /**
     * Releases WifiMulticastLock help for umundo.
     * Perform this only when user leaves the application.
     */
    public void releaseMulticastLock() {
        mMulticastLock.release();
    }

    /**
     * Unique id for this device
     */
    public static String getUUID() {
        return mUUID;
    }

    public class TestPublishing implements Runnable {

        @Override
        public void run() {

            while (testPublishing != null) {
                // Generate a random entry
                Entry entry = EntryTest.getDataPoint();

                mPublisher.send(JsonFactory.toJson(entry).getBytes());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
