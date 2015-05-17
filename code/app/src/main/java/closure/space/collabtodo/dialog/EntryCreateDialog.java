package closure.space.collabtodo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.rey.material.widget.EditText;

import closure.space.collabtodo.helper.NumberFactory;
import closure.space.collabtodo.helper.Procedures;
import closure.space.collabtodo.models.Entry;
import closure.space.collabtodo.params.Local;
import space.closure.collaborativetodo.R;

/**
 * Created by praveen on 10/5/15.
 */
public class EntryCreateDialog extends Dialog implements View.OnClickListener {
    Context context;
    String listid;
    int priority = Local.ENTRY_PRIORITY_DEFAULT;

    EditText entryTitleWidget;
    TextView prioValueWidget;


    public EntryCreateDialog(Context context, String listid) {
        super(context);
        this.context = context;
        this.listid = listid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Customize dialog
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // No title
        setContentView(R.layout.dialog_entry_new);

        // Init widgets
        entryTitleWidget = (EditText) findViewById(R.id.entry_new_name);
        prioValueWidget = (TextView) findViewById(R.id.entry_new_prio);
        findViewById(R.id.entry_new_prio_incr).setOnClickListener(this);
        findViewById(R.id.entry_new_prio_decr).setOnClickListener(this);
        findViewById(R.id.entry_new_save_btn).setOnClickListener(this);
        prioValueWidget.setText(String.valueOf(priority));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.entry_new_prio_incr:
                increasePriority();
                prioValueWidget.setText(String.valueOf(priority));
                break;
            case R.id.entry_new_prio_decr:
                decreasePriority();
                prioValueWidget.setText(String.valueOf(priority));
                break;
            case R.id.entry_new_save_btn:
                String entryName = entryTitleWidget.getText().toString();
                if (entryName.length() < Local.ENTRY_NAME_MIN_CHARS) {
                    entryTitleWidget.setHelper(
                            context.getString(R.string.entry_new_name_helper,
                                    Local.ENTRY_NAME_MIN_CHARS));
                    break;
                }

                // Create a new entry
                Entry nEntry = new Entry(
                        NumberFactory.uniqueGlobal(),
                        entryName,
                        listid);
                nEntry.setEntryPriority(priority);
                Procedures.Local.updateEntry(nEntry);

                dismiss();
                break;
        }
    }

    void increasePriority() {
        if (priority < Local.ENTRY_PRIORITY_MAX)
            priority++;
    }

    void decreasePriority() {
        if (priority > Local.ENTRY_PRIORITY_MIN)
            priority--;
    }
}
