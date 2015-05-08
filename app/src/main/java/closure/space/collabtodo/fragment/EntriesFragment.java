package closure.space.collabtodo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import closure.space.collabtodo.database.TodoListDao;
import closure.space.collabtodo.models.TodoList;
import space.closure.collaborativetodo.R;

/**
 * <p/>
 * Entries Fragment to handle display and actions on TodoLists
 * <p/>
 * Created by praveen on 8/5/15.
 */
public class EntriesFragment extends Fragment {

    Context context;

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

        return rootView;
    }
}
