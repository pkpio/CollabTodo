package closure.space.collabtodo.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import closure.space.collabtodo.fragment.EntriesFragment;
import closure.space.collabtodo.fragment.TodoListsFragment;
import closure.space.collabtodo.models.Entry;
import space.closure.collaborativetodo.R;

public class MainActivity extends BaseNavigationActivity implements Interfaces.EntryUpdater,
        Interfaces.TodoListUpdater, Interfaces.UIUpdater {
    EntriesFragment entriesFragment;
    TodoListsFragment todoListsFragment;

    String listid;
    String listname;

    // This acts like a handle for UI updates from other places
    public static Interfaces.UIUpdater UIUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
