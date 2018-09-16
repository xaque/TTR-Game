package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import shared.Results;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public abstract class HandlerBase implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody());
        Object obj = Serializer.deserializeObject(isr);
        isr.close();

        Results r = runCommand(obj);

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStreamWriter osr = new OutputStreamWriter(httpExchange.getResponseBody());
        Serializer.serializeResults(r, osr);
        osr.close();
    }

    public abstract Results runCommand(Object obj);

}
