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
    private int destinationCardRoutePoints;

    private ArrayList<TrainCard> trainCards = new ArrayList<>();
    private ArrayList<DestinationCard> destinationCards = new ArrayList<>();

    private ArrayList<DestinationCard> drawnDestinationCards = new ArrayList<>();

    private boolean hasDiscardedInitialDestinationCards;

    private ArrayList<Observer> observers = new ArrayList<>();

    public Player(String name, String authToken){
        this.name = name;
        this.authToken = authToken;
        this.points = 0;
        this.destinationCardRoutePoints = 0;
        this.trainTokens = 45;
        this.hasDiscardedInitialDestinationCards = false;
    }

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.destinationCardRoutePoints = 0;
        this.trainTokens = 45;
        this.hasDiscardedInitialDestinationCards = false;
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

    public int getDestinationCardRoutePoints() {
        return this.destinationCardRoutePoints;
    }

    public void addDestinationCardRoutePoints(int points) {
        this.destinationCardRoutePoints += points;
    }

    public void subtractDestinationCardRoutePoints(int points) {
        this.destinationCardRoutePoints -= points;
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

    public void addTrainTokens(int trainTokensToAdd){
        trainTokens -= trainTokensToAdd;
        notifyObservers();
    }

    public void subtractTrainTokens(int trainTokensToSubtract){
        trainTokens -= trainTokensToSubtract;
        notifyObservers();
    }

    public ArrayList<TrainCard> getTrainCards() {
        return trainCards;
    }

    public void setTrainCards(ArrayList<TrainCard> trainCards) {
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

    public void removeDestinationCards(ArrayList<DestinationCard> _cards) {//throws Exception{
        for(int i = 0; i < _cards.size(); i++) {
            DestinationCard card = _cards.get(i);
            for(int j = 0; j < destinationCards.size(); j++) {
                DestinationCard cardFromHand = destinationCards.get(j);
                if(card.getCity1().equals(cardFromHand.getCity1())
                        && card.getCity2().equals(cardFromHand.getCity2())) {
                    destinationCards.remove(j);
                    i++;
                    j = 0;
                }
            }
        }
        //throw new Exception("The player does not have this card!");
    }

    public ArrayList<DestinationCard> getDrawnDestinationCards() {
        return drawnDestinationCards;
    }

    public void setDrawnDestinationCards(ArrayList<DestinationCard> destinationCards) {
        this.drawnDestinationCards = destinationCards;
    }

    public ArrayList<DestinationCard> getDestinationCards() {
        return destinationCards;
    }

    public void setDestinationCards(ArrayList<DestinationCard> destinationCards) {
        this.destinationCards = destinationCards;
    }

    public void addDestinationCards(ArrayList<DestinationCard> newCards){
        System.out.println("Added " + Integer.toString(newCards.size()) + " destCards.");
        destinationCards.addAll(newCards);
        notifyObservers();
    }

    public boolean hasDiscardedInitialDestinationCards() {
        return this.hasDiscardedInitialDestinationCards;
    }

    public void setHasDiscardedInitialDestinationCards(boolean val) {
        this.hasDiscardedInitialDestinationCards = val;
    }

    public ArrayList<Observer> getObservers(){
        return observers;
    }

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
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
