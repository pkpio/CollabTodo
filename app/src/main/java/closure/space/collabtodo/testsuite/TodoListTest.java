package closure.space.collabtodo.testsuite;

import java.util.ArrayList;
import java.util.List;

import closure.space.collabtodo.helper.NumberFactory;
import closure.space.collabtodo.models.Entry;
import closure.space.collabtodo.models.EntryPriority;
import closure.space.collabtodo.models.TodoList;

/**
 * To get test data for TodoLists
 * <p/>
 * Created by praveen on 8/5/15.
 */
public class TodoListTest {

    /**
     * Generate a test TodoList data point
     *
     * @return sample TodoList data point
     */
    public static TodoList getDataPoint() {
        TodoList tl = new TodoList();
        tl.setListid(String.valueOf(NumberFactory.random()));
        tl.setListname("Test List #" + NumberFactory.random(1, 999));
        tl.setEntries(EntryTest.getDataPoints(NumberFactory.random(1, 10)));
        return tl;
    }

    /**
     * Generate a list of TodoList data point
     *
     * @param size Size of list
     * @return sample List of TodoList data points
     */
    public static List<TodoList> getDataPoints(int size) {
        List<TodoList> tls = new ArrayList<TodoList>();

        for (int i = 0; i < size; i++)
            tls.add(getDataPoint());

        return tls;
    }
}
