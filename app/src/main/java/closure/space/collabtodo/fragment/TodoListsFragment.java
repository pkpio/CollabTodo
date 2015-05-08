package closure.space.collabtodo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import junit.framework.TestSuite;

import java.util.List;

import closure.space.collabtodo.database.TodoListDao;
import closure.space.collabtodo.models.TodoList;
import closure.space.collabtodo.testsuite.TodoListTest;
import space.closure.collaborativetodo.R;

/**
 * <p/>
 * TodoLists Fragment to handle display and actions on TodoLists
 * <p/>
 * Created by praveen on 8/5/15.
 */
public class TodoListsFragment extends Fragment {

    Context context;
    List<TodoList> mTodoLists;
    TodoListsListAdapter mTodoListsAdapter;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        this.context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todolists, container,
                false);

        // List all TodoLists
        mTodoLists = TodoListTest.getDataPoints(10);

        // Initialize list and adapter
        ListView mTodoListView = (ListView) rootView.findViewById(R.id.todolists_list);
        mTodoListsAdapter = new TodoListsListAdapter(context);
        mTodoListView.setAdapter(mTodoListsAdapter);

        return rootView;
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
    }

    static class ViewHolder {
        TextView listName;
        TextView entryCount;
    }
}
