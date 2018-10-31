package cs340.game.shared;

import java.io.IOException;
import java.io.Serializable;

import cs340.game.shared.data.Data;
import cs340.game.shared.results.Results;

public class Serializer {

    /**
     * Serialize CommandData to Base64 string
     * @param data The CommandData to serialize
     * @return The serialized string in Base64
     * @throws IOException if serialization fails
     */
    public static String serializeData(Data data) throws IOException {
        return serializeObject(data);
    }

    /**
     * Serialize Results to Base64 string
     * @param r The Results to serialize
     * @return The serialized string in Base64
     * @throws IOException if serialization fails
     */
    public static String serializeResults(Results r) throws IOException{
        return serializeObject(r);
    }

    /**
     * Deserialize CommandData from Base64 string
     * @param s The serialized string in Base64
     * @returnThe deserialized CommandData
     * @throws IOException if deserialization fails
     * @throws ClassNotFoundException if CommandData object is not found in the Base64 data
     */
    public static CommandData deserializeCommandData(String s) throws IOException, ClassNotFoundException {
        return deserializeObject(s, CommandData.class);
    }

    public static Data deserializeData(String s) throws IOException, ClassNotFoundException {
        return deserializeObject(s, Data.class);
    }

    /**
     * Deserialize Results from Base64 string
     * @param s The serialized string in Base64
     * @return The deserialized Results
     * @throws IOException if deserialization fails
     * @throws ClassNotFoundException if Results object is not found in the Base64 data
     */
    public static Results deserializeResults(String s) throws IOException, ClassNotFoundException{
        return deserializeObject(s, Results.class);
    }

    /**
     * Generic method for serializing any object to Base64
     * @param obj The object to serialize
     * @return The serialized string in Base64
     * @throws IOException if serialization fails
     */
    private static String serializeObject(Serializable obj) throws IOException {
        return Base64.encodeObject(obj);
    }

    /**
     * Generic method for deserializing any object type from Base64
     * @param s The serialized string in Base64
     * @param type The type to convert to
     * @param <T> Generic type
     * @return The deserialized object
     * @throws IOException if deserialization fails
     * @throws ClassNotFoundException if type casting fails
     */
    private static <T> T deserializeObject(String s, Class<T> type) throws IOException, ClassNotFoundException {
        return type.cast( Base64.decodeToObject(s) );
    }

}
