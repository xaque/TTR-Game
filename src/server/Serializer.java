package server;

import com.google.gson.Gson;
import shared.CommandData;
import shared.Results;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Serializer {

    private static Gson gson = new Gson();

    public static void serializeResults(Results o, OutputStreamWriter osw) {
        gson.toJson(o, osw);
    }
    public static CommandData deserializeCommandData(InputStreamReader isr){
        return gson.fromJson(isr, CommandData.class);
    }
}
