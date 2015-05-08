package space.closure.collaborativetodo;


import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.widget.TextView;

import org.umundo.core.Discovery;
import org.umundo.core.Discovery.DiscoveryType;
import org.umundo.core.Message;
import org.umundo.core.Node;
import org.umundo.core.Publisher;
import org.umundo.core.Receiver;
import org.umundo.core.Subscriber;

public class MainActivity extends BaseNavigationActivity {
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    TextView tv;
    Thread testPublishing;
    Discovery disc;
    Node node;
    Publisher fooPub;
    Subscriber fooSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.log);
        tv.setText("");

        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifi != null) {
            WifiManager.MulticastLock mcLock = wifi.createMulticastLock("mylock");
            mcLock.acquire();
            // mcLock.release();
        } else {
            Log.v("android-umundo", "Cannot get WifiManager");
        }

        // System.loadLibrary("umundoNativeJava");
        System.loadLibrary("umundoNativeJava_d");

        disc = new Discovery(DiscoveryType.MDNS);

        node = new Node();
        disc.add(node);

        fooPub = new Publisher("pingpong");
        node.addPublisher(fooPub);

        fooSub = new Subscriber("pingpong");
        fooSub.setReceiver(new TestReceiver());
        node.addSubscriber(fooSub);

        testPublishing = new Thread(new TestPublishing());
        testPublishing.start();
    }

    public class TestPublishing implements Runnable {

        @Override
        public void run() {
            String message = "This is foo from android";
            Message msg = new Message();
            msg.setData(message.getBytes());
            msg.putMeta("Zolo", "Test meta contents");
            while (testPublishing != null) {
                fooPub.send(msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(tv.getText() + "o");
                    }
                });
            }
        }
    }

    public class TestReceiver extends Receiver {
        public void receive(Message msg) {

            for (String key : msg.getMeta().keySet()) {
                Log.i("umundo", key + ": " + msg.getMeta(key));
            }
            Log.i("umundo", new String(msg.getData()));
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv.setText(tv.getText() + "i");
                }
            });
        }
    }
}
