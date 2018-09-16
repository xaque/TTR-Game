package server;

import com.sun.net.httpserver.HttpServer;
import shared.CommonData;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerCommunicator {
    private HttpServer server;
    public void run(){
        try {
            server = HttpServer.create(new InetSocketAddress(CommonData.PORT_NUMBER), CommonData.MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            System.out.println("Could not create HTTP server: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);
        server.createContext(CommonData.TOLOWERCASE_URI, new ToLowerCaseHandler());
        server.createContext(CommonData.TRIM_URI, new TrimHandler());
        server.createContext(CommonData.PARSEDOUBLE_URI, new ParseDoubleHandler());

        server.start();
    }
}
