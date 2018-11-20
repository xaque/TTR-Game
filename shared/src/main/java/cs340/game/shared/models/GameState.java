package cs340.game.shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import cs340.game.shared.GameHistoryAction;
import cs340.game.shared.GameHistoryActionList;

public class GameState extends Observable implements Serializable {

    private String name;
    private ArrayList<Route> routes;
    private ArrayList<Player> players;
    private ArrayList<TrainCard> faceUpCards;
    private GameHistoryActionList history;
    private ArrayList<Observer> observers;
    private boolean isChanged;
    private int trainCardDeckSize;
    private String currentTurnPlayer;
    private boolean oneTrainCardDrawn;
    private boolean finalRound;
    private String lastPlayerInFinalRound;
    private boolean gameOver;
    private String longestTrackPlayerName;

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
        currentTurnPlayer = this.players.get(0).getName();
        oneTrainCardDrawn = false;
        finalRound = false;
        lastPlayerInFinalRound = null;
        gameOver = false;
    }

    public GameState(String name, ArrayList<Player> players){
        this.name = name;
        this.players = players;
        routes = new ArrayList<>();
        faceUpCards = new ArrayList<>();
        history = new GameHistoryActionList();
        observers = new ArrayList<>();
        isChanged = false;
        trainCardDeckSize = 0;
        destinationTicketDeckSize = 0;
        currentTurnPlayer = this.players.get(0).getName();
        oneTrainCardDrawn = false;
        finalRound = false;
        lastPlayerInFinalRound = null;
        gameOver = false;
    }

    public String getGameName() {
        return name;
    }

    public void setGameName(String name) {
        this.name = name;
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public void updateRoute(Route newRoute){
        for(int i = 0; i < routes.size(); i++){
            if(newRoute.equals(routes.get(i))){
                routes.set(i, newRoute);
                //TODO is there connection between how routes are managed in the server and the routes that are passed back to the client?
            }
        }

        isChanged = true;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayerByName(String name) {
        for(Player player: this.players) {
            if(player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public void setPlayers(ArrayList<Player> players) {
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

    public ArrayList<TrainCard> getFaceUpCards() {
        return faceUpCards;
    }

    public void setFaceUpCards(ArrayList<TrainCard> faceUpCards) {
        this.faceUpCards = faceUpCards;
        notifyObservers();
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

    public boolean isFinalRound() {
        return this.finalRound;
    }

    public void setFinalRound(boolean finalRound) {
        this.finalRound = finalRound;
    }

    public String getLastPlayerInFinalRound() {
        return this.lastPlayerInFinalRound;
    }

    public void setLastPlayerInFinalRound(String name) {
        this.lastPlayerInFinalRound = name;
    }

    public String nextPlayerTurn() {
        int currentPlayerIndex = this.players.indexOf(this.currentTurnPlayer);
        currentPlayerIndex++;
        if(currentPlayerIndex == this.players.size()) {
            currentPlayerIndex = 0;
        }
        this.currentTurnPlayer = this.players.get(currentPlayerIndex).getName();
        return this.currentTurnPlayer;
    }

    public String getCurrentTurnPlayer() {
        return currentTurnPlayer;
    }

    public void setCurrentTurnPlayer(String currentTurnPlayer) {
        this.currentTurnPlayer = currentTurnPlayer;
    }

    public boolean isOneTrainCardDrawn() {
        return oneTrainCardDrawn;
    }

    public void setOneTrainCardDrawn(boolean oneTrainCardDrawn) {
        this.oneTrainCardDrawn = oneTrainCardDrawn;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkForChanges(){
        if(isChanged){
            notifyObservers();
            isChanged = false;
        }
    }

    @Override
    public synchronized void addObserver(Observer o) {
        System.out.println("Adding Observer");
        observers.add(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(){
        System.out.println("Notifying Observers");

        for (Observer obj : observers) {
            obj.update(this, name);
        }
    }
}
