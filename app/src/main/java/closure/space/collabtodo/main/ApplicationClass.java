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

import closure.space.collabtodo.params.Local;

/**
 * Created by praveen on 9/5/15.
 */
public class ApplicationClass extends SugarApp {
    Discovery mDiscovery;
    WifiManager.MulticastLock mMulticastLock;

    Node mNode;
    Publisher mPublisher;
    Subscriber mSubscriber;


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
    }

    /**
     * A custom umundo receiver to handle all Subscribed traffic
     */
    public class CustomUmunodReceiver extends Receiver {
        public void receive(Message msg) {

            for (String key : msg.getMeta().keySet()) {
                Log.i(Local.UMUNDO_LOG_TAG, key + ": " + msg.getMeta(key));
            }
            Log.i(Local.UMUNDO_LOG_TAG, new String(msg.getData()));

            // -TODO- Implement a way to pass callbacks on new data to higher layers
        }
    }

    // -TODO- Implement multicast lock release
    // mcLock.release();
}
