package cs340.game.shared.models;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private String name;
    private List<Route> routes = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private List<TrainCard> faceUpCards = new ArrayList<>();

    public String getGameName() {
        return name;
    }

    public void setGameName(String name) {
        this.name = name;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<TrainCard> getFaceUpCards() {
        return faceUpCards;
    }

    public void setFaceUpCards(List<TrainCard> faceUpCards) {
        this.faceUpCards = faceUpCards;
    }
}
