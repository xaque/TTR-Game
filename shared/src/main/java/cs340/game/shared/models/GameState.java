package cs340.game.shared.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cs340.game.shared.GameHistoryActionList;

public class GameState extends Observable {

    private String name;
    private List<Route> routes = new ArrayList<>();
    private List<Player> players = new ArrayList<>();
    private List<TrainCard> faceUpCards = new ArrayList<>();
    private GameHistoryActionList history;

    private List<Observer> observers = new ArrayList<>();

    private boolean isChanged = false;

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

    public void updateHistory(GameHistoryActionList newHistory){
        this.history.getActions().addAll(newHistory.getActions());
    }

    public void checkForChanges(){
        if(isChanged){
            notifyObservers();
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
