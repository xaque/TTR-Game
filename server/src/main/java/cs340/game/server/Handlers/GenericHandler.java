package cs340.game.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Scanner;

import cs340.game.server.Commands.GameCommands.ChatCommand;
import cs340.game.server.Commands.GameCommands.DrawDestincationCardCommand;
import cs340.game.server.Commands.GameCommands.ReturnDestinationCardCommand;
import cs340.game.server.Commands.LobbyCommands.CreateGameCommand;
import cs340.game.server.Commands.LobbyCommands.JoinGameCommand;
import cs340.game.server.Commands.LoginCommands.LoginCommand;
import cs340.game.server.Commands.LoginCommands.RegisterCommand;
import cs340.game.server.Commands.PollerCommands.LobbyPollerCommand;
import cs340.game.server.Commands.iCommand;
import cs340.game.shared.results.Results;
import cs340.game.shared.Serializer;
import cs340.game.shared.data.Data;

public class GenericHandler implements HttpHandler {
    private Data getExchangeData(HttpExchange httpExchange){
        Scanner s = new Scanner(httpExchange.getRequestBody()).useDelimiter("\\A");
        String raw = s.hasNext() ? s.next() : "";
        Data data = null;
        try {
            data = Serializer.deserializeData(raw);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Data data = getExchangeData(httpExchange);

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
            case CHAT:
                cmd = new ChatCommand();
                break;
            case DRAW_DESTINATION_CARD:
                cmd = new DrawDestincationCardCommand();
                break;
            case DISCARD_DESTINATION_CARD:
                cmd = new ReturnDestinationCardCommand();
                break;
        }
        Results r = cmd.execute(data);

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStreamWriter osw = new OutputStreamWriter(httpExchange.getResponseBody());
        osw.write(Serializer.serializeResults(r));
        osw.close();
    }
}
