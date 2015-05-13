package closure.space.collabtodo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import closure.space.collabtodo.database.EntryDao;
import closure.space.collabtodo.database.TodoListDao;
import closure.space.collabtodo.dialog.EntryCreateDialog;
import closure.space.collabtodo.dialog.EntryMenuDialog;
import closure.space.collabtodo.models.Entry;
import closure.space.collabtodo.models.EntryPriority;
import closure.space.collabtodo.models.TodoList;
import closure.space.collabtodo.testsuite.EntryTest;
import closure.space.collabtodo.testsuite.TodoListTest;
import space.closure.collaborativetodo.R;

/**
 * <p/>
 * Entries Fragment to handle display and actions on TodoLists
 * <p/>
 * Created by praveen on 8/5/15.
 */
public class EntriesFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    Context context;
    String mListid;
    List<Entry> mEntries;
    EntryListAdapter mEntryListAdapter;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        this.context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_entries, container,
                false);

        // List all Entries in a TodoList
        updateEntryList(mListid);

        // Initialize list and adapter
        ListView mEntryListView = (ListView) rootView.findViewById(R.id.entries_list);
        mEntryListAdapter = new EntryListAdapter(context);
        mEntryListView.setAdapter(mEntryListAdapter);
        mEntryListView.setOnItemClickListener(this);

        // Setup add button
        rootView.findViewById(R.id.entry_add_btn).setOnClickListener(this);

        return rootView;
    }


    public void updateEntryList(String listid) {
        this.mListid = listid;

        // Set entries to be listed
        if (mListid == null)
            // Sample data for now
            mEntries = EntryTest.getDataPoints(20);
        else
            mEntries = EntryDao.getEntries(listid);

        // Sort them by priority
        // -TODO- Implement sorting based on entries average priority here

        if (mEntryListAdapter != null)
            mEntryListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int[] dialogLocation = new int[2];
        view.getLocationOnScreen(dialogLocation);
        EntryMenuDialog emd = new EntryMenuDialog(context, mEntries.get(position), dialogLocation);
        emd.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.entry_add_btn:
                EntryCreateDialog ecd = new EntryCreateDialog(context, mListid);
                ecd.show();
                break;
        }

    }


    /**
     * Custom list adapter for Entry listing
     *
     * @author praveen
     */
    public class EntryListAdapter extends BaseAdapter {
        private final Context context;

        public EntryListAdapter(Context context) {
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
                convertView = inflater.inflate(R.layout.listitem_entry,
                        parent, false);

                // Set widgets
                viewHolder.entryName = (TextView) convertView
                        .findViewById(R.id.entry_name);
                viewHolder.entryPrio = (TextView) convertView
                        .findViewById(R.id.entry_prio);

                // Apply a tag for future references
                convertView.setTag(viewHolder);
            }

            // Inflated before. Get from tag.
            else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            // Set values
            viewHolder.entryName.setText(getItem(position).getEntryName());

            // Set Avg. value and a proper background
            int avg = getItem(position).getMeanPriority();
            viewHolder.entryPrio.setText(String.valueOf(avg));
            switch (avg) {
                case 5:
                    viewHolder.entryPrio.setBackgroundResource(R.drawable.circle_red);
                    break;
                case 4:
                    viewHolder.entryPrio.setBackgroundResource(R.drawable.circle_orange);
                    break;
                case 3:
                    viewHolder.entryPrio.setBackgroundResource(R.drawable.circle_green);
                    break;
                case 2:
                    viewHolder.entryPrio.setBackgroundResource(R.drawable.circle_blue);
                    break;
                case 1:
                default:
                    viewHolder.entryPrio.setBackgroundResource(R.drawable.circle_grey);
                    break;
            }

            return convertView;
        }

        @Override
        public int getCount() {
            return mEntries.size();
        }

        @Override
        public Entry getItem(int position) {
            return mEntries.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    static class ViewHolder {
        TextView entryName;
        TextView entryPrio;
    }
}
