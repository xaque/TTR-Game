package cs340.game.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Scanner;

import cs340.game.server.Commands.GameCommands.ChatCommand;
import cs340.game.server.Commands.GameCommands.ClaimRouteCommand;
import cs340.game.server.Commands.GameCommands.DiscardTrainCardsCommand;
import cs340.game.server.Commands.GameCommands.DrawDestinationCardCommand;
import cs340.game.server.Commands.GameCommands.DrawTrainCardFaceUpCommand;
import cs340.game.server.Commands.GameCommands.DrawTrainCardFromDeckCommand;
import cs340.game.server.Commands.GameCommands.ReturnDestinationCardCommand;
import cs340.game.server.Commands.LobbyCommands.CreateGameCommand;
import cs340.game.server.Commands.LobbyCommands.JoinGameCommand;
import cs340.game.server.Commands.LobbyCommands.StartGameCommand;
import cs340.game.server.Commands.LoginCommands.LoginCommand;
import cs340.game.server.Commands.LoginCommands.RegisterCommand;
import cs340.game.server.Commands.PollerCommands.GamePollerCommand;
import cs340.game.server.Commands.PollerCommands.LobbyPollerCommand;
import cs340.game.server.Commands.iCommand;
import cs340.game.server.Factories.DAOFactory;
import cs340.game.server.Factories.FlatFileDAOFactory;
import cs340.game.server.Factories.InMemoryDAOFactory;
import cs340.game.server.Factories.SQLDAOFactory;
import cs340.game.shared.CommonData;
import cs340.game.shared.Serializer;
import cs340.game.shared.data.Data;
import cs340.game.shared.results.Results;

public class GenericHandler implements HttpHandler {

    private final DAOFactory daoFactory;

    public GenericHandler(){
        switch (CommonData.PERSISTENCE_TYPE){
            case "sqlite":
                this.daoFactory = new SQLDAOFactory();
                break;
            case "file":
                this.daoFactory = new FlatFileDAOFactory();
                break;
            case "memory":
                this.daoFactory = new InMemoryDAOFactory();
                break;
            default:
                this.daoFactory = new InMemoryDAOFactory();
                break;
        }
    }

    private Data getExchangeData(HttpExchange httpExchange){
        Scanner s = new Scanner(httpExchange.getRequestBody()).useDelimiter("\\A");
        String raw = s.hasNext() ? s.next() : "";
        Data data = null;
        try {
            data = Serializer.deserializeData(raw);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Data data = getExchangeData(httpExchange);

        Results r = runCommand(data);

        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        OutputStreamWriter osw = new OutputStreamWriter(httpExchange.getResponseBody());
        osw.write(Serializer.serializeResults(r));
        osw.close();
    }

    public Results runCommand(Data data){
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
                cmd = new DrawDestinationCardCommand();
                break;
            case DISCARD_DESTINATION_CARD:
                cmd = new ReturnDestinationCardCommand();
                break;
            case DRAW_TRAIN_CARD_DECK:
                cmd = new DrawTrainCardFromDeckCommand();
                break;
            case DRAW_TRAIN_CARD_FACE_UP:
                cmd = new DrawTrainCardFaceUpCommand();
                break;
            case DISCARD_TRAIN_CARD:
                cmd = new DiscardTrainCardsCommand();
                break;
            case CLAIM_ROUTE:
                cmd = new ClaimRouteCommand();
                break;
            case CLAIM_ROUTE_GREY:
                cmd = new ClaimRouteCommand();
                break;
            case START_GAME:
                cmd = new StartGameCommand();
                break;
            case GAME_POLL:
                cmd = new GamePollerCommand();
                break;
            default:
                System.out.println("None");
                break;
        }
        Results r = cmd.execute(data, this.daoFactory);

        return r;
    }
}
