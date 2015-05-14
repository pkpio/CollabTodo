package closure.space.collabtodo.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.List;

import closure.space.collabtodo.database.TodoListDao;
import closure.space.collabtodo.models.TodoList;
import space.closure.collaborativetodo.R;

/**
 * Created by Jasmin on 14/05/2015.
 */
public class EntryMoveDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.entry_move_dialog_title)
                .setItems(getToDoListNames(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    public String[] getToDoListNames(){
        List<TodoList> mTodoLists = TodoListDao.list();
        String[] toDoListNames = new String[mTodoLists.size()];
        for (int i = 0; i<mTodoLists.size(); i++) {
            toDoListNames[i] = (mTodoLists.get(i).getListname());
        }
        return toDoListNames;
    }


}
