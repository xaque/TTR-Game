package shared;

import java.io.*;
import java.util.Base64;

public class Serializer {

    public static String serializeCommandData(CommandData cd) throws IOException {
        return serializeObject(cd);
    }

    public static String serializeResults(Results r) throws IOException{
        return serializeObject(r);
    }

    public static CommandData deserializeCommandData(String s) throws IOException, ClassNotFoundException {
        return deserializeObject(s, CommandData.class);
    }

    public static Results deserializeResults(String s) throws IOException, ClassNotFoundException{
        return deserializeObject(s, Results.class);
    }

    private static String serializeObject(Serializable obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private static <T> T deserializeObject(String s, Class<T> type) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        T o = type.cast(ois.readObject());
        ois.close();
        return o;
    }

}
