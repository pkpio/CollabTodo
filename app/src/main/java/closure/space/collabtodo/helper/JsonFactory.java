package closure.space.collabtodo.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by praveen on 9/5/15.
 */
public class JsonFactory {

    /**
     * Converts an object to json string. Values are set from the field values of object.
     * <p/>
     * Note:
     * The major difference compared to normal gson way is that this converts only fields
     * which have a SerializedName set. This works best for Objects used as Sugar models
     * simultaneously.
     *
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj) {
        return createExGson().toJson(obj);
    }

    /**
     * Converts a json string into an object of given class type.
     *
     * @param json     json string
     * @param classOfT class of the destination object
     * @param <T>      type of the returning object
     * @return Object of type T
     * @see #toObjects(String, Class)
     */
    public static <T> T toObject(String json, Class<T> classOfT) {
        return createExGson().fromJson(json, classOfT);
    }

    /**
     * Converts a json string into a List of objects of given class type.
     *
     * @param json     json string
     * @param classOfT class of the single object in the destination List type
     * @param <T>      type of the List of objects to return
     * @return List of objects of type T
     * @see #toObject(String, Class)
     */
    public static <T> List<T> toObjects(String json, Class<T> classOfT) {
        return createExGson().fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    /**
     * Creates a Gson object with exclusion strategy set to support Sugar objects
     *
     * @return Gson object
     */
    private static Gson createExGson() {
        GsonExclude ex = new GsonExclude();
        return new GsonBuilder()
                .addDeserializationExclusionStrategy(ex)
                .addSerializationExclusionStrategy(ex).create();
    }
}
