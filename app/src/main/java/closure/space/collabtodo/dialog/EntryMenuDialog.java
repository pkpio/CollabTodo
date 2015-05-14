package closure.space.collabtodo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.rey.material.widget.CheckBox;
import com.rey.material.widget.EditText;

import closure.space.collabtodo.database.EntryDao;
import closure.space.collabtodo.helper.NumberFactory;
import closure.space.collabtodo.helper.Procedures;
import closure.space.collabtodo.main.Interfaces;
import closure.space.collabtodo.models.Entry;
import closure.space.collabtodo.params.Local;
import space.closure.collaborativetodo.R;

/**
 * Created by praveen on 10/5/15.
 */
public class EntryMenuDialog extends Dialog implements View.OnClickListener,
        CheckBox.OnCheckedChangeListener, Dialog.OnDismissListener {
    Context context;
    Dialog.OnDismissListener dismissListener;

    Entry mEntry;
    String listid;
    int[] location;

    // Widgets
    CheckBox entryDoneWidget;
    TextView entryPrioWidget;

    // Priority values
    int priority = Local.ENTRY_PRIORITY_DEFAULT;
    Boolean priorityChanged = false;

    /**
     * @param context         Context of the activity
     * @param entry           Entry for which menu is shown
     * @param location        Location of the dialog
     * @param dismissListener To notify caller of this dismiss callback.
     *                        The dialog implements it's own callback so pass here rather than
     *                        setting an onDismissListener from caller.
     * @param listid          current listid being shown
     */
    public EntryMenuDialog(Context context, Entry entry, int[] location,
                           Dialog.OnDismissListener dismissListener, String listid) {
        super(context);
        this.context = context;
        this.mEntry = entry;
        this.location = location;
        this.dismissListener = dismissListener;
        this.listid = listid;
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
        setContentView(R.layout.dialog_entry_menu);

        // Init widgets
        entryPrioWidget = (TextView) findViewById(R.id.entry_action_prio);
        entryDoneWidget = (CheckBox) findViewById(R.id.entry_action_done);
        entryDoneWidget.setOnCheckedChangeListener(this);
        findViewById(R.id.entry_action_move).setOnClickListener(this);
        findViewById(R.id.entry_action_delete).setOnClickListener(this);
        findViewById(R.id.entry_action_prio_incr).setOnClickListener(this);
        findViewById(R.id.entry_action_prio_decr).setOnClickListener(this);
        this.setOnDismissListener(this);

        // Init values
        this.priority = mEntry.getEntryPriority();
        entryPrioWidget.setText(String.valueOf(priority));
        entryDoneWidget.setChecked(mEntry.isEntryDone());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.entry_action_delete:
                Procedures.Local.deleteEntry(mEntry.getEntryid());
                dismiss();
                break;
            case R.id.entry_action_move:
                // -TODO- Implement moving
                dismiss();
                break;
            case R.id.entry_action_prio_incr:
                increasePriority();
                break;
            case R.id.entry_action_prio_decr:
                decreasePriority();
                break;
        }
    }

    public void onDismiss(DialogInterface dialogInterface) {
        // Because calling on each change is comm. intensive
        if (priorityChanged) {
            mEntry.setEntryPriority(priority);
            Procedures.Local.updateEntry(mEntry);
        }
        dismissListener.onDismiss(dialogInterface);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked)
            mEntry.setEntryDone(true);
        else
            mEntry.setEntryDone(false);
        Procedures.Local.updateEntry(mEntry);
        dismiss();
    }

    void increasePriority() {
        if (priority < Local.ENTRY_PRIORITY_MAX)
            priority++;
        entryPrioWidget.setText(String.valueOf(priority));
        priorityChanged = true;
    }

    void decreasePriority() {
        if (priority > Local.ENTRY_PRIORITY_MIN)
            priority--;
        entryPrioWidget.setText(String.valueOf(priority));
        priorityChanged = true;
    }
}
