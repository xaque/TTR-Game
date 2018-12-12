package cs340.game.shared;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import cs340.game.shared.data.Data;
import cs340.game.shared.results.Results;

/**
 * This class is for serializing and deserializing objects in Base64.
 */

public class Serializer {

    /**
     * Serialize Data to Base64 string
     * @param data The Data to serialize
     * @pre data != null
     * @pre data and all members of data implement Serializable
     * @post return != null
     * @return The serialized string in Base64
     * @throws IOException if serialization fails
     */
    public static String serializeData(Data data) throws IOException {
        return serializeObject(data);
    }

    /**
     * Serialize Results to Base64 string
     * @param r The Results to serialize
     * @pre r != null
     * @pre r and all members of r implement Serializable
     * @post return != null
     * @return The serialized string in Base64
     * @throws IOException if serialization fails
     */
    public static String serializeResults(Results r) throws IOException{
        return serializeObject(r);
    }

    /**
     * Deserialize Data from Base64 string
     * @param s The serialized string in Base64
     * @pre s != null
     * @pre s is a valid Base64 string
     * @pre s was generated using this Serializer
     * @post return != null
     * @return The deserialized Data
     * @throws IOException if deserialization fails
     */
    public static Data deserializeData(String s) throws IOException {
        Data data;
        try{
            data = deserializeObject(s, Data.class);
        }catch (ClassNotFoundException e){
            //If Data.class not found
            return null;
        }
        return data;
    }

    /**
     * Deserialize Results from Base64 string
     * @param s The serialized string in Base64
     * @pre s != null
     * @pre s is a valid Base64 string
     * @pre s was generated using this Serializer
     * @post return != null
     * @return The deserialized Results
     * @throws IOException if deserialization fails
     */
    public static Results deserializeResults(String s) throws IOException{
        Results results;
        try{
            results = deserializeObject(s, Results.class);
        }catch (ClassNotFoundException e){
            //If Results.class not found
            return null;
        }
        return results;
    }

    /**
     * Generic method for serializing any object to Base64
     * @param obj The object to serialize
     * @pre obj != null
     * @pre all members of obj are Serializable
     * @post return != null
     * @return The serialized string in Base64
     * @throws IOException if serialization fails
     */
    public static String serializeObject(Serializable obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        return Base64.encodeToString(baos.toByteArray(), 0);
    }

    /**
     * Generic method for deserializing any object type from Base64
     * @param s The serialized string in Base64
     * @param type The type to convert to
     * @param <T> Generic type
     * @pre s != null
     * @pre s is a valid Base64 string
     * @pre s was generated using this Serializer
     * @pre type implements Serializable
     * @post return != null
     * @return The deserialized object
     * @throws IOException if deserialization fails
     * @throws ClassNotFoundException if type not found
     */
    public static <T> T deserializeObject(String s, Class<T> type) throws IOException, ClassNotFoundException {
        byte[] data = Base64.decode(s, 0);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        T obj = type.cast(ois.readObject());
        ois.close();
        return obj;
    }

}
