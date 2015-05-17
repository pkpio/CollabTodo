package closure.space.collabtodo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.rey.material.widget.EditText;

import closure.space.collabtodo.helper.NumberFactory;
import closure.space.collabtodo.helper.Procedures;
import closure.space.collabtodo.models.Entry;
import closure.space.collabtodo.models.TodoList;
import closure.space.collabtodo.params.Local;
import space.closure.collaborativetodo.R;

/**
 * Created by praveen on 13.05.15.
 */
public class ListCreateDialog extends Dialog implements View.OnClickListener {
    Context context;

    EditText listTitleWidget;

    public ListCreateDialog(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Customize dialog
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // No title
        setContentView(R.layout.dialog_list_new);

        // Init widgets
        listTitleWidget = (EditText) findViewById(R.id.todolist_new_name);
        findViewById(R.id.todolist_new_save_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.todolist_new_save_btn:
                String listName = listTitleWidget.getText().toString();
                if (listName.length() < Local.LIST_NAME_MIN_CHARS) {
                    listTitleWidget.setHelper(
                            context.getString(R.string.todolist_new_name_helper,
                                    Local.LIST_NAME_MIN_CHARS));
                    break;
                }

                // Create a new list
                TodoList nList = new TodoList(NumberFactory.uniqueGlobal(), listName);
                Procedures.Local.createList(nList);

                dismiss();
                break;
        }
    }
}
