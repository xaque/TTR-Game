package cs340.game.shared.models;

import java.io.Serializable;
import java.util.ArrayList;

/*
For now, perhaps we don't really do a diff, but give the whole state of the game.
Then we can see if there is a bandwidth issue. Then we can implement it as a diff if necessary.
 */
public class GameStateDiff implements Serializable {
    private ArrayList<Player> playerList;
    private ArrayList<DestinationCard> destinationCardDeck;
    private ArrayList<Route> routeList;
    private ArrayList<TrainCard> trainCardDeck;
    private ArrayList<TrainCard> faceUpCards;

    public GameStateDiff(){}

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public ArrayList<DestinationCard> getDestinationCardDeck() {
        return destinationCardDeck;
    }

    public void setDestinationCardDeck(ArrayList<DestinationCard> destinationCardDeck) {
        this.destinationCardDeck = destinationCardDeck;
    }

    public ArrayList<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(ArrayList<Route> routeList) {
        this.routeList = routeList;
    }

    public ArrayList<TrainCard> getTrainCardDeck() {
        return trainCardDeck;
    }

    public void setTrainCardDeck(ArrayList<TrainCard> trainCardDeck) {
        this.trainCardDeck = trainCardDeck;
    }

    public ArrayList<TrainCard> getFaceUpCards() {
        return faceUpCards;
    }

    public void setFaceUpCards(ArrayList<TrainCard> faceUpCards) {
        this.faceUpCards = faceUpCards;
    }
}
