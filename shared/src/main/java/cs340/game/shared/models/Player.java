package cs340.game.shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cs340.game.shared.Color;

public class Player extends Observable implements Serializable {

    private String name;
    private String authToken;

    private int trainTokens;
    private int points;

    private List<TrainCard> trainCards = new ArrayList<>();
    private List<DestinationCard> destinationCards = new ArrayList<>();

    private List<Observer> observers = new ArrayList<>();

    public Player(String name, String authToken){
        this.name = name;
        this.authToken = authToken;
        this.points = 0;
        this.trainTokens = 45;
    }

    public Player(String name) {
        this.name = name;
        this.authToken = null;
        this.points = 0;
        this.trainTokens = 45;
    }

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
        notifyObservers();
    }

    public void addPoints(int pointsToAdd){
        this.points += pointsToAdd;
        notifyObservers();
    }

    public void subtractPoints(int pointsToSubtract){
        this.points -= pointsToSubtract;
        notifyObservers();
    }

    public int getTrainTokens(){
        return trainTokens;
    }

    public void setTrainTokens(int trainTokens){
        this.trainTokens = trainTokens;
        notifyObservers();
    }

    public void subtractTrainTokens(int trainTokensToSubtract){
        trainTokens -= trainTokensToSubtract;
        notifyObservers();
    }

    public List<TrainCard> getTrainCards() {
        return trainCards;
    }

    public void setTrainCards(List<TrainCard> trainCards) {
        this.trainCards = trainCards;
        notifyObservers();
    }

    public boolean hasSufficientCards(Color colorNeeded, int numberNeeded){

        int count = 0;
        for(int i = 0; i < trainCards.size(); i++){
            TrainCard card = trainCards.get(i);
            if(card.getColor() == colorNeeded || card.getColor() == Color.WILD){
                count++;
            }
        }

        return (count >= numberNeeded);
    }

    public void addTrainCard(TrainCard newCard){
        trainCards.add(newCard);
        notifyObservers();
    }

    public TrainCard getTrainCard(Color color) throws Exception{

        for(int i = 0; i < trainCards.size(); i++){
            TrainCard card = trainCards.get(i);
            if(card.getColor() == color){
                return card;
            }
        }

        throw new Exception("The player does not have this card!");
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

    public void removeDestinationCards(List<DestinationCard> _cards) {//throws Exception{
        destinationCards.removeAll(_cards);
        //throw new Exception("The player does not have this card!");
    }

    public List<DestinationCard> getDestinationCards() {
        return destinationCards;
    }

    public void setDestinationCards(List<DestinationCard> destinationCards) {
        this.destinationCards = destinationCards;
    }

    public void addDestinationCards(List<DestinationCard> newCards){
        destinationCards.addAll(newCards);
    }

    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(){

        for (Observer obj : observers) {
            obj.update(this, name);
        }
    }
}
