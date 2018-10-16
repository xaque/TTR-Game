package cs340.game.shared.models;

import java.util.ArrayList;
import java.util.List;

import cs340.game.shared.Color;

public class Player {

    private String name;
    private String authToken;

    private int points;

    private List<TrainCard> trainCards = new ArrayList<>();
    private List<DestinationCard> destinationCards = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int pointsToAdd){
        this.points += pointsToAdd;
    }

    public void subtractPoints(int pointsToSubtract){
        this.points -= pointsToSubtract;
    }

    public List<TrainCard> getTrainCards() {
        return trainCards;
    }

    public void setTrainCards(List<TrainCard> trainCards) {
        this.trainCards = trainCards;
    }

    public boolean hasSufficientCards(Color colorNeeded, int numberNeeded){

        int count = 0;
        for(int i = 0; i < trainCards.size(); i++){
            TrainCard card = trainCards.get(i);
            if(card.getColor() == colorNeeded || card.getColor() == Color.LOCOMOTIVE){
                count++;
            }
        }

        return (count >= numberNeeded);
    }

    public void removeTrainCard(Color color) throws Exception{

        for(int i = 0; i < trainCards.size(); i++){
            TrainCard card = trainCards.get(i);
            if(card.getColor() == color){
                trainCards.remove(i);
                return;
            }
        }

        throw new Exception("The player does not have this card!");
    }

    public List<DestinationCard> getDestinationCards() {
        return destinationCards;
    }

    public void setDestinationCards(List<DestinationCard> destinationCards) {
        this.destinationCards = destinationCards;
    }
}
