package closure.space.collabtodo.testsuite;

import java.util.ArrayList;
import java.util.List;

import closure.space.collabtodo.helper.NumberFactory;
import closure.space.collabtodo.models.Entry;

/**
 * To get test data for entries
 * <p/>
 * Created by praveen on 8/5/15.
 */
public class EntryTest {

    /**
     * Generate a test Entry data point
     *
     * @return sample Entry data point
     */
    public static Entry getDataPoint() {
        Entry e = new Entry();
        e.setEntryid(String.valueOf(NumberFactory.random()));
        e.setEntryName("Test Entry #" + NumberFactory.random(1, 999));
        e.setEntryPriority(NumberFactory.random(1, 5));
        return e;
    }

    /**
     * Generate a list of Entry data point
     *
     * @param size Size of list
     * @return sample List of Entry data points
     */
    public static List<Entry> getDataPoints(int size) {
        List<Entry> es = new ArrayList<Entry>();

        for (int i = 0; i < size; i++)
            es.add(getDataPoint());

        return es;
    }
}
