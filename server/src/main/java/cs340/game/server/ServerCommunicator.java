package cs340.game.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import cs340.game.shared.CommonData;

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
        server.createContext(CommonData.LOGIN_URI, ech);
        server.createContext(CommonData.LOBBY_URI, ech);
        server.createContext(CommonData.POLLER_URI, ech);

        server.start();
    }
}
