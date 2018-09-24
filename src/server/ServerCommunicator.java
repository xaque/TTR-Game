package server;

import com.sun.net.httpserver.HttpServer;
import shared.CommonData;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerCommunicator {
    private HttpServer server;

    /**
     * Initialize the HttpServer port and uri contexts
     */
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
        ExecCommandHandler ech = new ExecCommandHandler();
        //TODO should there only be one context since they all go to the same handler?
        server.createContext(CommonData.TOLOWERCASE_URI, ech);
        server.createContext(CommonData.TRIM_URI, ech);
        server.createContext(CommonData.PARSEDOUBLE_URI, ech);

        server.start();
    }
}
