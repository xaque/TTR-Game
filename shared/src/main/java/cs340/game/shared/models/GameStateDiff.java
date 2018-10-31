package cs340.game.shared.models;

import java.io.Serializable;
import java.util.List;

/*
For now, perhaps we don't really do a diff, but give the whole state of the game.
Then we can see if there is a bandwidth issue. Then we can implement it as a diff if necessary.
 */
public class GameStateDiff implements Serializable {
    private List<Player> playerList;
    private List<DestinationCard> destinationCardDeck;
    private List<Route> routeList;
    private List<TrainCard> trainCardDeck;
    private List<TrainCard> faceUpCards;

    public GameStateDiff(){}

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<DestinationCard> getDestinationCardDeck() {
        return destinationCardDeck;
    }

    public void setDestinationCardDeck(List<DestinationCard> destinationCardDeck) {
        this.destinationCardDeck = destinationCardDeck;
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    public List<TrainCard> getTrainCardDeck() {
        return trainCardDeck;
    }

    public void setTrainCardDeck(List<TrainCard> trainCardDeck) {
        this.trainCardDeck = trainCardDeck;
    }

    public List<TrainCard> getFaceUpCards() {
        return faceUpCards;
    }

    public void setFaceUpCards(List<TrainCard> faceUpCards) {
        this.faceUpCards = faceUpCards;
    }
}
