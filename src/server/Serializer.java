package server;

import com.google.gson.Gson;
import shared.Results;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Serializer {

    private static Gson gson = new Gson();

    public static void serializeResults(Results o, OutputStreamWriter osr) {
        gson.toJson(o, osr);
    }
    public static Object deserializeObject(InputStreamReader isr){
        return gson.fromJson(isr, Object.class);
    }
}
