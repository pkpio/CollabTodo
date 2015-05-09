package closure.space.collabtodo.main;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

import closure.space.collabtodo.fragment.EntriesFragment;
import closure.space.collabtodo.fragment.TodoListsFragment;
import closure.space.collabtodo.models.Entry;
import space.closure.collaborativetodo.R;

public class MainActivity extends BaseNavigationActivity implements Interfaces.EntryUpdater, Interfaces.TodoListUpdater {
    EntriesFragment entriesFragment;
    TodoListsFragment todoListsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get fresh fragment instances
        entriesFragment = new EntriesFragment();
        todoListsFragment = new TodoListsFragment();

        // Set fragments to layouts
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, entriesFragment);
        ft.replace(R.id.left_navigation, todoListsFragment);
        ft.commit();
    }

    public void updateEntryList(List<Entry> entries, String todoListName) {
        entriesFragment.updateEntryList(entries);
        setDrawerState(false, todoListName);
    }

    public void updateTodoList() {
        todoListsFragment.updateTodoList();
    }
}
