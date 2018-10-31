package cs340.game.shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cs340.game.shared.GameHistoryAction;
import cs340.game.shared.GameHistoryActionList;

public class GameState extends Observable implements Serializable {

    private String name;
    private List<Route> routes;
    private List<Player> players;
    private List<TrainCard> faceUpCards;
    private GameHistoryActionList history;
    private List<Observer> observers;
    private boolean isChanged;
    private int trainCardDeckSize;

    public int getTrainCardDeckSize() {
        return trainCardDeckSize;
    }

    public void setTrainCardDeckSize(int trainCardDeckSize) {
        this.trainCardDeckSize = trainCardDeckSize;
    }

    public int getDestinationTicketDeckSize() {
        return destinationTicketDeckSize;
    }

    public void setDestinationTicketDeckSize(int destinationTicketDeckSize) {
        this.destinationTicketDeckSize = destinationTicketDeckSize;
    }

    private int destinationTicketDeckSize;

    public GameState(String name){
        this.name = name;
        routes = new ArrayList<>();
        players = new ArrayList<>();
        faceUpCards = new ArrayList<>();
        history = new GameHistoryActionList();
        observers = new ArrayList<>();
        isChanged = false;
        trainCardDeckSize = 0;
        destinationTicketDeckSize = 0;
    }

    public GameState(String name, List<Player> players){
        this.name = name;
        this.players = players;
        routes = new ArrayList<>();
        faceUpCards = new ArrayList<>();
        history = new GameHistoryActionList();
        observers = new ArrayList<>();
        isChanged = false;
        trainCardDeckSize = 0;
        destinationTicketDeckSize = 0;
    }

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

    public void updateRoute(Route newRoute){
        for(int i = 0; i < routes.size(); i++){
            if(newRoute.equals(routes.get(i))){
                routes.set(i, newRoute);
            }
        }

        isChanged = true;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void updatePlayer(Player newPlayer){
        for(int i = 0; i < players.size(); i++){
            if(newPlayer.getName().equals(players.get(i).getName())){
                players.set(i, newPlayer);
            }
        }

        isChanged = true;
    }

    public List<TrainCard> getFaceUpCards() {
        return faceUpCards;
    }

    public void setFaceUpCards(List<TrainCard> faceUpCards) {
        this.faceUpCards = faceUpCards;
    }

    public GameHistoryActionList getHistory() {
        return history;
    }

    public void setHistory(GameHistoryActionList history) {
        this.history = history;
    }

    public void addHistoryAction(GameHistoryAction action) {
        this.history.addAction(action);
    }

    /*public void updateHistory(GameHistoryActionList newHistory){
        this.history.getActions().addAll(newHistory.getActions());
    }*/

    public void checkForChanges(){
        if(isChanged){
            notifyObservers();
            isChanged = false;
        }
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
