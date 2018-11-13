package cs340.game.shared.data;

import cs340.game.shared.Color;
import cs340.game.shared.CommandType;
import cs340.game.shared.models.Route;

public class ClaimRouteData extends Data{
    private String username;
    private Route route;
    private Color color;

    /**
     * This constructor is to be used when the player is claiming a colored route.
     * @param username the username of the player claiming the route
     * @param route the Route to be claimed
     */
    public ClaimRouteData(String username, Route route) {
        //commandType should always be CLAIM_ROUTE
        this.commandType = CommandType.CLAIM_ROUTE;
        this.username = username;
        this.route = route;
    }

    /**
     * This constructor is to be used when the player is claiming a grey route.
     * @param username the username of the player claiming the route
     * @param route the Route to be claimed
     * @param color the Color that the player has chosen to use for the Route
     */
    public ClaimRouteData(String username, Route route, Color color){
        //commandType should always be CLAIM_ROUTE_GREY
        this.commandType = CommandType.CLAIM_ROUTE_GREY;
        this.username = username;
        this.route = route;
        this.color = color;
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

    public void setRoute(Route route) {
        this.route = route;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
