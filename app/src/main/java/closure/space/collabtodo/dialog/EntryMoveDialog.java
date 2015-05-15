package closure.space.collabtodo.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.List;

import closure.space.collabtodo.database.EntryDao;
import closure.space.collabtodo.database.TodoListDao;
import closure.space.collabtodo.models.Entry;
import closure.space.collabtodo.models.TodoList;
import closure.space.collabtodo.params.Local;
import space.closure.collaborativetodo.R;

/**
 * Created by Jasmin on 14/05/2015.
 */
public class EntryMoveDialog extends DialogFragment {

    /**
     * Creates a new instance of EntryMoveDialog and puts the entryid to its bundle
     *
     * @param entryid
     * @return
     */
    public static final EntryMoveDialog newInstance(String entryid) {
        EntryMoveDialog dialog = new EntryMoveDialog();
        Bundle bundle = new Bundle(1);
        bundle.putString(Local.ENTRY_ID, entryid);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //list of current todolists
        final List<TodoList> mTodoLists = TodoListDao.list();

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.entry_move_dialog_title)
                .setItems(getToDoListNames(mTodoLists), new DialogInterface.OnClickListener() {

                    //Move entry to the selected list
                    public void onClick(DialogInterface dialog, int selected) {
                        Entry mEntry = EntryDao.getEntry(getArguments().getString(Local.ENTRY_ID));
                        mEntry.setListid(mTodoLists.get(selected).getListid());
                        mEntry.save();
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    /**
     * Takes list of todolists and returns list of their names
     *
     * @param mTodoLists
     * @return
     */
    public String[] getToDoListNames(List<TodoList> mTodoLists) {
        String[] toDoListNames = new String[mTodoLists.size()];
        for (int i = 0; i < mTodoLists.size(); i++) {
            toDoListNames[i] = (mTodoLists.get(i).getListname());
        }
        return toDoListNames;
    }


}
