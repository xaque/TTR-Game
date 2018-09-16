package client;

import com.google.gson.Gson;
import shared.Results;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Serializer {
    private static Gson gson = new Gson();
    public static void serializeObject(Object o, OutputStreamWriter osr){
        gson.toJson(o, osr);
    }
    public static Results deserializeResults(InputStreamReader isr){
        return gson.fromJson(isr, Results.class);
    }
}
