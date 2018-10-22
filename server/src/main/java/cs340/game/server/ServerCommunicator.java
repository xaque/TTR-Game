package cs340.game.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import cs340.game.server.Handlers.GameHandler;
import cs340.game.server.Handlers.LobbyHandler;
import cs340.game.server.Handlers.LoginHandler;
import cs340.game.server.Handlers.PollerHandler;
import cs340.game.shared.CommonData;

public class ServerCommunicator {
    private HttpServer server;

    /**
     * Initialize the HttpServer port and uri contexts
     */
    public void run(){

        System.out.println("Starting Server");

        try {
            server = HttpServer.create(new InetSocketAddress(CommonData.PORT_NUMBER), CommonData.MAX_WAITING_CONNECTIONS);
            System.out.println("Listening on Port " + CommonData.PORT_NUMBER);
        }
        catch (IOException e) {
            System.out.println("Could not create HTTP server: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);
        server.createContext(CommonData.LOGIN_URI, new LoginHandler());
        server.createContext(CommonData.LOBBY_URI, new LobbyHandler());
        server.createContext(CommonData.POLLER_URI, new PollerHandler());
        server.createContext(CommonData.GAME_URI, new GameHandler());

        server.start();
    }
}
