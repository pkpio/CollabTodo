package closure.space.collabtodo.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.rey.material.widget.EditText;

import closure.space.collabtodo.helper.Procedures;
import closure.space.collabtodo.main.MainActivity;
import space.closure.collaborativetodo.R;

/**
 * Created by Jane Doe on 14.05.2015.
 */
public class ListDeleteDialog extends Dialog implements View.OnClickListener {
    Context context;
    String listid;
    Activity a;

    public ListDeleteDialog(Context context, String listid, Activity a) {
        super(context);
        this.context = context;
        this.listid = listid;
        this.a = a;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Customize dialog
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // No title
        setContentView(R.layout.dialog_list_delete);

        // Init widgets
        findViewById(R.id.todolist_delete_sure).setOnClickListener(this);
        findViewById(R.id.todolist_cancel_delete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.todolist_delete_sure:
                Procedures.Local.deleteList(listid);
                // Remove title
                ((MainActivity) a).setTitle(context.getString(R.string.pick_list));
                dismiss();
                break;
            case R.id.todolist_cancel_delete:
                dismiss();
                break;
        }
    }
}
