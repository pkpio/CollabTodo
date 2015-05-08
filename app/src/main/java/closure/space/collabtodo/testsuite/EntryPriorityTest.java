package closure.space.collabtodo.testsuite;

import java.util.ArrayList;
import java.util.List;

import closure.space.collabtodo.helper.NumberFactory;
import closure.space.collabtodo.models.EntryPriority;

/**
 * To get test data for entry priorities
 * <p/>
 * Created by praveen on 8/5/15.
 */
public class EntryPriorityTest {

    /**
     * Generate a test EntryPriority data point
     *
     * @return sample EntryPriority data point
     */
    public static EntryPriority getDataPoint() {
        EntryPriority ep = new EntryPriority();
        ep.setEntryid("");
        ep.setPriority(NumberFactory.random(1, 5));
        ep.setUserid(String.valueOf(NumberFactory.random()));
        return ep;
    }

    /**
     * Generate a list of EntryPriority data point
     *
     * @param size Size of list
     * @return sample List of EntryPriority data points
     */
    public static List<EntryPriority> getDataPoints(int size) {
        List<EntryPriority> eps = new ArrayList<EntryPriority>();

        for (int i = 0; i < size; i++)
            eps.add(getDataPoint());

        return eps;
    }


}
