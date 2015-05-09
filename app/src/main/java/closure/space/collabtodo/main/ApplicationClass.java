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

import closure.space.collabtodo.helper.JsonFactory;
import closure.space.collabtodo.models.Entry;
import closure.space.collabtodo.params.Local;
import closure.space.collabtodo.testsuite.EntryTest;

/**
 * Created by praveen on 9/5/15.
 */
public class ApplicationClass extends SugarApp {
    Discovery mDiscovery;
    WifiManager.MulticastLock mMulticastLock;

    Node mNode;
    Publisher mPublisher;
    Subscriber mSubscriber;

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

    private void initUmundo() {

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
        public void receive(Message msg) {

            //for (String key : msg.getMeta().keySet()) {
            //    Log.i(Local.UMUNDO_LOG_TAG, key + ": " + msg.getMeta(key));
            //}
            Entry entry = JsonFactory.toObject(new String(msg.getData()), Entry.class);
            Log.i(Local.UMUNDO_LOG_TAG, entry.getEntryName());

            // -TODO- Implement a way to pass callbacks on new data to higher layers
        }
    }

    // -TODO- Implement multicast lock release
    // mcLock.release();

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
