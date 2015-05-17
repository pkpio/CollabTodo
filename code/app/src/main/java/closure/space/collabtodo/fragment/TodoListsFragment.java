package closure.space.collabtodo.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.List;

import closure.space.collabtodo.database.TodoListDao;
import closure.space.collabtodo.dialog.EntryMenuDialog;
import closure.space.collabtodo.dialog.ListCreateDialog;
import closure.space.collabtodo.main.Interfaces;
import closure.space.collabtodo.models.TodoList;
import closure.space.collabtodo.testsuite.TodoListTest;
import space.closure.collaborativetodo.R;

/**
 * <p/>
 * TodoLists Fragment to handle display and actions on TodoLists
 * <p/>
 * Created by praveen on 8/5/15.
 */
public class TodoListsFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, DialogInterface.OnDismissListener {

    Context context;
    Interfaces.EntryUpdater mEntryUpdater;

    List<TodoList> mTodoLists = new ArrayList<TodoList>();
    TodoListsListAdapter mTodoListsAdapter;
    View listEmtpyWidget;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        this.context = getActivity();
        this.mEntryUpdater = (Interfaces.EntryUpdater) a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todolists, container,
                false);

        // Initialize list and adapter
        ListView mTodoListView = (ListView) rootView.findViewById(R.id.todolists_list);
        listEmtpyWidget = (View) rootView.findViewById(R.id.list_empty);
        mTodoListsAdapter = new TodoListsListAdapter(context);
        mTodoListView.setAdapter(mTodoListsAdapter);
        mTodoListView.setOnItemClickListener(this);

        // List all TodoLists
        updateTodoList();

        // Update entries from the first TodoList
        if (mTodoLists != null && mTodoLists.size() != 0)
            mEntryUpdater.updateEntryList(
                    mTodoLists.get(0).getListid(),
                    mTodoLists.get(0).getListname());

        // Setup add button
        rootView.findViewById(R.id.todolists_add).setOnClickListener(this);

        return rootView;
    }

    /**
     * Update the currently shown TodoList
     */
    public void updateTodoList() {
        // List all TodoLists
        mTodoLists = TodoListDao.list();
        if (mTodoListsAdapter != null)
            mTodoListsAdapter.notifyDataSetChanged();
    }

    /**
     * Get the list of TodoList currently shown on UI
     *
     * @return List of TodoList currently on UI
     */
    public List<TodoList> getTodoList() {
        return mTodoLists;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mEntryUpdater.updateEntryList(
                mTodoLists.get(position).getListid(),
                mTodoLists.get(position).getListname());
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.todolists_add:
                ListCreateDialog lcd = new ListCreateDialog(context);
                lcd.setOnDismissListener(this);
                lcd.show();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        updateTodoList();
    }

    /**
     * Custom list adapter for TodoList listing
     *
     * @author praveen
     */
    public class TodoListsListAdapter extends BaseAdapter {
        private final Context context;

        public TodoListsListAdapter(Context context) {
            this.context = context;
            if (mTodoLists == null || mTodoLists.size() == 0)
                listEmtpyWidget.setVisibility(View.VISIBLE);
            else
                listEmtpyWidget.setVisibility(View.GONE);
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder viewHolder;

            // Inflating for first time
            if (convertView == null) {
                viewHolder = new ViewHolder();

                // Get Inflater and inflate layout
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listitem_todolist,
                        parent, false);

                // Set widgets
                viewHolder.listName = (TextView) convertView
                        .findViewById(R.id.todolist_name);
                viewHolder.entryCount = (TextView) convertView
                        .findViewById(R.id.todolist_entrycount);

                // Apply a tag for future references
                convertView.setTag(viewHolder);
            }

            // Inflated before. Get from tag.
            else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // Set values
            viewHolder.listName.setText(getItem(position).getListname());
            viewHolder.entryCount.setText(String.valueOf(getItem(position).getEntries().size()));

            return convertView;
        }

        @Override
        public int getCount() {
            return mTodoLists.size();
        }

        @Override
        public TodoList getItem(int position) {
            return mTodoLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void notifyDataSetChanged() {
            if (mTodoLists == null || mTodoLists.size() == 0)
                listEmtpyWidget.setVisibility(View.VISIBLE);
            else
                listEmtpyWidget.setVisibility(View.GONE);
            super.notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        TextView listName;
        TextView entryCount;
    }
}
