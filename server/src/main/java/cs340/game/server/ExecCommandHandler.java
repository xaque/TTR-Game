package cs340.game.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import cs340.game.server.Commands.*;
import cs340.game.server.Commands.LobbyCommands.CreateGameCommand;
import cs340.game.server.Commands.LobbyCommands.JoinGameCommand;
import cs340.game.server.Commands.LoginCommands.LoginCommand;
import cs340.game.server.Commands.LoginCommands.RegisterCommand;
import cs340.game.server.Commands.PollerCommands.LobbyPollerCommand;
import cs340.game.shared.*;
import cs340.game.shared.data.Data;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class ExecCommandHandler implements HttpHandler {
    /**
     * Convert request and execute the command using the command pattern
     * @param httpExchange Object from which to get request info and send response
     * @throws IOException if serialization to CommandData object fails
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Scanner s = new Scanner(httpExchange.getRequestBody()).useDelimiter("\\A");
        String raw = s.hasNext() ? s.next() : "";
        Data data = null;
        try {
            data = Serializer.deserializeData(raw);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        iCommand cmd = null;
        switch (data.getCommandType()){
            case REGISTER:
                cmd = new RegisterCommand();
                break;
            case LOGIN:
                cmd = new LoginCommand();
                break;
            case CREATE_GAME:
                cmd = new CreateGameCommand();
                break;
            case JOIN_GAME:
                cmd = new JoinGameCommand();
                break;
            case LOBBY_POLL:
                cmd = new LobbyPollerCommand();
                break;
        }
        Results r = cmd.execute(data);

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStreamWriter osw = new OutputStreamWriter(httpExchange.getResponseBody());
        osw.write(Serializer.serializeResults(r));
        osw.close();
    }
}
