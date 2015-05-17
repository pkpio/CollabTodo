package closure.space.collabtodo.main;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.rey.material.widget.SnackBar;

import java.util.List;

import closure.space.collabtodo.dialog.ListDeleteDialog;
import closure.space.collabtodo.fragment.EntriesFragment;
import closure.space.collabtodo.fragment.TodoListsFragment;
import closure.space.collabtodo.models.Entry;
import space.closure.collaborativetodo.R;

public class MainActivity extends BaseNavigationActivity implements Interfaces.EntryUpdater,
        Interfaces.TodoListUpdater, Interfaces.UIUpdater, DialogInterface.OnDismissListener {
    Context context;
    EntriesFragment entriesFragment;
    TodoListsFragment todoListsFragment;

    String listid;
    String listname;

    // Widgets
    SnackBar mSnackbar;

    // This acts like a handle for UI updates from other places
    public static Interfaces.UIUpdater UIUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init widgets
        mSnackbar = (SnackBar) findViewById(R.id.main_snackbar);

        // Get fresh fragment instances
        entriesFragment = new EntriesFragment();
        todoListsFragment = new TodoListsFragment();
        this.UIUpdater = this;

        // Set fragments to layouts
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, entriesFragment);
        ft.replace(R.id.left_navigation, todoListsFragment);
        ft.commit();
    }

    public void updateEntryList(String listid, String listname) {
        this.listid = listid;
        this.listname = listname;
        entriesFragment.updateEntryList(listid);
        setDrawerState(false, listname);
    }

    public void updateTodoList() {
        todoListsFragment.updateTodoList();
    }

    /**
     * A thread safe call to update UI
     */
    public void updateUI() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                entriesFragment.updateEntryList(listid); // Update entries
                updateTodoList();   // Update list
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                if (listid == null) {
                    return true;
                }
                ListDeleteDialog lcd = new ListDeleteDialog(MainActivity.this, listid, this);
                lcd.setOnDismissListener(this);
                lcd.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        //nothing happens
    }

    public SnackBar getSnackBar() {
        return this.mSnackbar;
    }
}
