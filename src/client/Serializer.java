package client;

import com.google.gson.Gson;
import shared.CommandData;
import shared.Results;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Serializer {
    private static Gson gson = new Gson();
    public static void serializeObject(CommandData cd, OutputStreamWriter osw){
        gson.toJson(cd, osw);
    }
    public static Results deserializeResults(InputStreamReader isr){
        return gson.fromJson(isr, Results.class);
    }
}
