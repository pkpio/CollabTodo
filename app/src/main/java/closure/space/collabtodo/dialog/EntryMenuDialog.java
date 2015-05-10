package closure.space.collabtodo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import closure.space.collabtodo.models.Entry;
import space.closure.collaborativetodo.R;

/**
 * Created by praveen on 10/5/15.
 */
public class EntryMenuDialog extends Dialog {
    Context context;
    Entry mEntry;
    int[] location;

    public EntryMenuDialog(Context context, Entry entry, int[] location) {
        super(context);
        this.context = context;
        this.mEntry = entry;
        this.location = location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Customize dialog
        requestWindowFeature(Window.FEATURE_NO_TITLE);                          // No title
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);     // No dimming
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.TOP | Gravity.RIGHT;     // Custom gravity
        layoutParams.x = 0;             //x position
        layoutParams.y = location[1];   //y position

        setContentView(R.layout.dialog_entrymenu);
    }
}
