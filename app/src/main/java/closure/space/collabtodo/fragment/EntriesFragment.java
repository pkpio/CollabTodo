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

import com.rey.material.widget.SnackBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import closure.space.collabtodo.database.EntryDao;
import closure.space.collabtodo.dialog.EntryCreateDialog;
import closure.space.collabtodo.dialog.EntryMenuDialog;
import closure.space.collabtodo.main.BaseNavigationActivity;
import closure.space.collabtodo.main.Interfaces;
import closure.space.collabtodo.main.MainActivity;
import closure.space.collabtodo.models.Entry;
import space.closure.collaborativetodo.R;

/**
 * <p/>
 * Entries Fragment to handle display and actions on TodoLists
 * <p/>
 * Created by praveen on 8/5/15.
 */
public class EntriesFragment extends Fragment implements AdapterView.OnItemClickListener,
        View.OnClickListener, DialogInterface.OnDismissListener {

    Context context;
    BaseNavigationActivity activity;
    Interfaces.TodoListUpdater mTodoListUpdater;

    String mListid;
    List<Entry> mEntries = new ArrayList<Entry>();
    EntryListAdapter mEntryListAdapter;
    View entryEmtpyWidget;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        this.context = getActivity();
        this.activity = (BaseNavigationActivity) a;
        this.mTodoListUpdater = (Interfaces.TodoListUpdater) a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_entries, container,
                false);

        // Initialize list and adapter
        ListView mEntryListView = (ListView) rootView.findViewById(R.id.entries_list);
        entryEmtpyWidget = (View) rootView.findViewById(R.id.entries_empty);
        mEntryListAdapter = new EntryListAdapter(context);
        mEntryListView.setAdapter(mEntryListAdapter);
        mEntryListView.setOnItemClickListener(this);

        // List all Entries in a TodoList
        updateEntryList(mListid);

        // Setup add button
        rootView.findViewById(R.id.entry_add_btn).setOnClickListener(this);

        return rootView;
    }


    public void updateEntryList(String listid) {
        this.mListid = listid;

        // Set entries to be listed
        if (mListid != null)
            mEntries = EntryDao.getEntries(listid);

        // Sort them by priority
        Collections.sort(mEntries, new Comparator<Entry>() {
            @Override
            public int compare(Entry entry1, Entry entry2) {
                Integer prio = entry1.getEntryPriority();
                return prio.compareTo(entry2.getEntryPriority());
            }
        });

        if (mEntryListAdapter != null)
            mEntryListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int[] dialogLocation = new int[2];
        view.getLocationOnScreen(dialogLocation);
        EntryMenuDialog emd = new EntryMenuDialog(context, activity, mEntries.get(position), dialogLocation,
                this, mListid);
        emd.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.entry_add_btn:
                if (mListid != null) {
                    EntryCreateDialog ecd = new EntryCreateDialog(context, mListid);
                    ecd.setOnDismissListener(this);
                    ecd.show();
                } else {
                    SnackBar snackBar = ((MainActivity) getActivity()).getSnackBar();
                    snackBar.applyStyle(R.style.SnackBarSingleLine)
                            .text(getString(R.string.umsg_no_list_picked))
                            .show();
                }
                break;
        }

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        //updateEntryList(this.mListid);
        //mTodoListUpdater.updateTodoList(); // Updates counts
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
            if (mEntries == null || mEntries.size() == 0)
                entryEmtpyWidget.setVisibility(View.VISIBLE);
            else
                entryEmtpyWidget.setVisibility(View.GONE);
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
                viewHolder.entryStrike = (TextView) convertView
                        .findViewById(R.id.entry_strike);

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
            viewHolder.entryPrio.setText(String.valueOf(getItem(position).getEntryPriority()));
            switch (getItem(position).getEntryPriority()) {
                case 1:
                    viewHolder.entryPrio.setBackgroundResource(R.drawable.circle_red);
                    break;
                case 2:
                    viewHolder.entryPrio.setBackgroundResource(R.drawable.circle_orange);
                    break;
                case 3:
                    viewHolder.entryPrio.setBackgroundResource(R.drawable.circle_lightorange);
                    break;
                case 4:
                    viewHolder.entryPrio.setBackgroundResource(R.drawable.circle_blue);
                    break;
                case 5:
                default:
                    viewHolder.entryPrio.setBackgroundResource(R.drawable.circle_grey);
                    break;
            }

            // Entry strike if need be
            if (getItem(position).isEntryDone())
                viewHolder.entryStrike.setVisibility(TextView.VISIBLE);
            else
                viewHolder.entryStrike.setVisibility(TextView.GONE);


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

        @Override
        public void notifyDataSetChanged() {
            if (mEntries == null || mEntries.size() == 0)
                entryEmtpyWidget.setVisibility(View.VISIBLE);
            else
                entryEmtpyWidget.setVisibility(View.GONE);
            super.notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        TextView entryName;
        TextView entryPrio;
        TextView entryStrike;
    }
}
