package cs340.game.shared.data;

import cs340.game.shared.CommandType;
import cs340.game.shared.models.Route;

public class ClaimRouteData extends Data{
    private String username;
    private Route route;

    public ClaimRouteData(String username, Route route) {
        //commandType should always be chat
        this.commandType = CommandType.CLAIM_ROUTE;
        this.username = username;
        this.route = route;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Route getRoute() {
        return route;
    }

    public void setChatContent(Route route) {
        this.route = route;
    }
}
