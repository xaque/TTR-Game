package cs340.game.server.Handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class PollerHandler extends GenericHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        //TODO create handler instead of using generic handler
        super.handle(httpExchange);
    }
}
