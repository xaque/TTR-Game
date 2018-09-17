package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import shared.CommandData;
import shared.Results;
import shared.Serializer;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class ExecCommandHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Scanner s = new Scanner(httpExchange.getRequestBody()).useDelimiter("\\A");
        String raw = s.hasNext() ? s.next() : "";
        CommandData cd = null;
        try {
            cd = Serializer.deserializeCommandData(raw);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        iCommand cmd = null;
        switch (cd.getType()){
            case TOLOWERCASE:
                cmd = new ToLowerCaseCommand(cd);
                break;
            case TRIM:
                cmd = new TrimCommand(cd);
                break;
            case PARSEDOUBLE:
                cmd = new ParseDoubleCommand(cd);
                break;
        }
        Results r = cmd.execute();

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStreamWriter osw = new OutputStreamWriter(httpExchange.getResponseBody());
        osw.write(Serializer.serializeResults(r));
        osw.close();
    }
}
