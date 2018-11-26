package cs340.game.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cs340.game.client.Presenters.MainActivityPresenter;
import cs340.game.shared.models.DestinationCard;
import cs340.game.shared.models.Game;
import cs340.game.shared.models.GameList;
import cs340.game.shared.models.GameState;
import cs340.game.shared.models.GameStateDiff;
import cs340.game.shared.models.Player;
import cs340.game.shared.models.Route;
import cs340.game.shared.models.TrainCard;
import cs340.game.shared.models.User;

/**
 * Contains models for the client side and stores relevant data for the client.
 */
public class ClientModelRoot extends Observable {
    // Observable methods:
    // public void addObserver(Observer o)
    // public void deleteObserver(Observer o)
    // public void notifyObservers()

    private ArrayList<Observer> observers = new ArrayList<>();

    // Start Singleton
    private static ClientModelRoot instance;

    private ClientModelRoot(){}

    public static ClientModelRoot getInstance() {

        if(instance == null){

            instance = new ClientModelRoot();
        }

        return instance;
    }
    // End Singleton

    private UserState userState = UserState.LOGGED_OUT;
    private User currentUser;
    private Game currentGame;
    private GameList games = new GameList();
    private ArrayList<String> messages;

    private Thread pollerThread;
    private int lobbySequenceNumber;
    private int gameSequenceNumber;

    private Player currentPlayer;
    private GameState currentGameState;

    public void InitializeGameState(Game game){
        ArrayList<String> users = game.getPlayers();
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<String> messages = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            String user = users.get(i);
            Player player = new Player(user);
            players.add(player);
        }

        currentGameState = new GameState(game.getName(), players);
    }

    public void updateGameState(GameState newState){
        ArrayList<Player> diffPlayers = newState.getPlayers();
        for(int i = 0; i < diffPlayers.size(); i++){
            System.out.println(diffPlayers.get(i).getName() + " " + diffPlayers.get(i).getTrainCards().size());
            currentGameState.updatePlayer(diffPlayers.get(i));

            if(diffPlayers.get(i).getName().equals(currentPlayer.getName())){
                System.out.println(diffPlayers.get(i).getName() + " ME!");
                String currentAuthToken = currentPlayer.getAuthToken();
                currentPlayer = diffPlayers.get(i);
                currentPlayer.setAuthToken(currentAuthToken);
                currentPlayer.notifyObservers();
            }
        }

        ArrayList<Route> diffRoutes = newState.getRoutes();
        for(int i = 0; i < diffRoutes.size(); i++){
            currentGameState.updateRoute(diffRoutes.get(i));
        }

        currentGameState.setFaceUpCards(newState.getFaceUpCards());

        System.out.println(newState.getHistory().getActions().size());
        currentGameState.setHistory(newState.getHistory());

        if(!currentGameState.isFinalRound() && newState.isFinalRound()){
            currentGameState.setFinalRound(true);
            currentGameState.setLastPlayerInFinalRound(newState.getLastPlayerInFinalRound());
            currentGameState.setChanged(true);
        }

        if(!currentGameState.isGameOver() && newState.isGameOver()){
            currentGameState.setGameOver(true);
            currentGameState.setChanged(true);
        }

        String currentTurn = currentGameState.getCurrentTurnPlayer();
        String newTurn = newState.getCurrentTurnPlayer();
        if(!currentTurn.equals(newTurn)){
            currentGameState.setCurrentTurnPlayer(newTurn);
            currentGameState.setChanged(true);
        }

        if(currentGameState.getTrainCardDeckSize() != newState.getTrainCardDeckSize()){
            currentGameState.setTrainCardDeckSize(newState.getTrainCardDeckSize());
            currentGameState.setChanged(true);
        }

        if(currentGameState.getDestinationTicketDeckSize() != newState.getDestinationTicketDeckSize()){
            currentGameState.setDestinationTicketDeckSize(newState.getDestinationTicketDeckSize());
            currentGameState.setChanged(true);
        }

        currentGameState.setLongestTrackPlayerNames(newState.getLongestTrackPlayerNames());

        currentGameState.checkForChanges();
    }

    public ArrayList<String> getAllMessages() {
        if(messages == null) {
            messages = new ArrayList<>();
        }
        return messages; };

    public GameState getCurrentGameState(){
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState){
        this.currentGameState = currentGameState;
    }

    public void addObserverToCurrentGameState(Observer o){
        currentGameState.addObserver(o);
    }

    public void removeObserverFromCurrentGameState(Observer o){
        currentGameState.deleteObserver(o);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {

        if(currentPlayer.getAuthToken() != null) {
            this.currentPlayer = currentPlayer;
        }
    }

    public void addObserverToCurrentPlayer(Observer o){
        currentPlayer.addObserver(o);
    }

    public void removeObserverFromCurrentPlayer(Observer o){
        currentPlayer.deleteObserver(o);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {

        if(this.currentUser == null) {
            this.currentUser = currentUser;
        }
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public int getLobbySequenceNumber() {
        return lobbySequenceNumber;
    }

    public void setLobbySequenceNumber(int lobbySequenceNumber) {
        this.lobbySequenceNumber = lobbySequenceNumber;
    }

    public int getGameSequenceNumber() {
        return gameSequenceNumber;
    }

    public void setGameSequenceNumber(int gameSequenceNumber) {
        this.gameSequenceNumber = gameSequenceNumber;
    }

    public UserState getUserState(){
        return userState;
    }

    public void setUserState(UserState userState){
        this.userState = userState;
    }

    public Game getGame(String gameName){
        return games.getGame(gameName);
    }

    public GameList getGames() {
        return games;
    }

    /**
     * Add a player to the list of players in a game.
     * @param userName the username of the player, used to identify them in the game
     * @param gameName the name of the game to which the player is being added
     */
    public void addPlayerToGame(String userName, String gameName){

        Game game = games.getGame(gameName);
        game.AddPlayer(userName);

        notifyObservers();
    }

    /**
     * Add a newly created game to the list of all games, then notify the observers that the
     * list of games has been changed.
     * @param game a newly created game to be added to the list of all games
     */
    public void addGame(Game game){

        games.addGame(game);

        notifyObservers();
    }

    /**
     * Sets the state of the game (whose name is passed in) to be started.
     * @param gameName the name of the game to be started
     */
    public void startGame(String gameName){

        Game game = getGame(gameName);
        game.setGameStarted(true);
    }

    /**
     * Updates the list of games by adding new games and/or updating previously existing games that
     * have had users added or removed. If games were added and/or updated, notify observers that
     * the list of games has been changed.
     * @param newGames a list of all games that have been newly created and/or changed in some way,
     *                 this is expected to not contain previously existing games that have not been
     *                 changed
     */
    public void updateGames(GameList newGames){

        System.out.println("Adding games: " + newGames.size());
        boolean changed = false;
        ArrayList<Game> _newGames = newGames.GetGames();
        for(int i = 0; i < _newGames.size(); i++){

            Game newGame = _newGames.get(i);
            String gameName = newGame.getName();
            if(games.gameExists(gameName)){

                ArrayList<String> newPlayers = newGame.getPlayers();
                if(addNewPlayersToGame(gameName, newPlayers)){

                    changed = true;
                }else{

                    // Check if game has been started
                    if(!games.getGame(gameName).isGameStarted() && newGame.isGameStarted()){
                        games.getGame(gameName).setGameStarted(true);
                        changed = true;
                    }
                }
            }else{

                games.addGame(newGame);
                changed = true;
            }
        }

        ArrayList<Game> listOfGames = games.GetGames();
        for(int i = 0; i < listOfGames.size(); i++){
            Game game = listOfGames.get(i);
            if(game.isGameStarted() && game.playerExistsInGame(currentUser.getUsername())){

                setUserState(UserState.IN_GAME);
                setCurrentGame(game);

                InGameFacade inGameFacade = InGameFacade.getInstance();
                inGameFacade.setupCurrentGameState(game);
                inGameFacade.initializeCurrentPlayer(currentUser);
            }
        }

        if(changed){

            System.out.println("Notifying Observers");
            notifyObservers();
        }
    }

    /**
     * Adds one or more players to a game if they are not already in the game. This is meant to be
     * a helper method for the updateGames() method.
     * @param gameName the name of the game to which players will be added
     * @param newPlayers a collection of the names of the new players that need to be added to the
     *                   game
     * @return true if at least one player was added to the game, false otherwise
     */
    private boolean addNewPlayersToGame(String gameName, ArrayList<String> newPlayers){

        boolean playerAdded = false;
        Game currentGame = games.getGame(gameName);
        for(int i = 0; i < newPlayers.size(); i++){

            String playerName = newPlayers.get(i);
            if(!currentGame.playerExistsInGame(playerName)){

                currentGame.AddPlayer(playerName);
                playerAdded = true;
            }
        }

        return playerAdded;
    }

    /**
     * Creates a new Thread to kick off the Poller, which will check for game updates, and starts it.
     * @see Poller
     */
    public void startPoller(MainActivityPresenter presenter){

        pollerThread = new Thread(new Poller());
        pollerThread.start();
        //presenter.getView().runOnUiThread(new Poller());

        // How to wait for the thread to finish
        //while(pollerThread.isAlive()){}
    }

    /**
     * Stops the Poller.
     * @see Poller
     */
    public void stopPoller(){

        if(pollerThread != null){

            pollerThread.stop();
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

        for(Observer obj : observers) {
            obj.update(this, games);
        }
    }

    // METHODS FOR DEMO
    public void addPointsToPlayer(Player player, int points){
        ArrayList<Player> players = currentGameState.getPlayers();
        for(int i = 0; i < players.size(); i++){

            Player p = players.get(i);
            if(p.getName().equals(player.getName())) {

                p.addPoints(points);
                if (p.getName().equals(currentPlayer.getName())) {
                    currentPlayer.addPoints(points);
                }
            }
        }
        currentPlayer.notifyObservers();
        currentGameState.notifyObservers();
    }

    public void subtractPointsFromPlayer(Player player, int points){
        ArrayList<Player> players = currentGameState.getPlayers();
        for(int i = 0; i < players.size(); i++){

            Player p = players.get(i);
            if(p.getName().equals(player.getName())) {

                p.subtractPoints(points);
                if (p.getName().equals(currentPlayer.getName())) {
                    currentPlayer.subtractPoints(points);
                }
            }
        }
        currentPlayer.notifyObservers();
        currentGameState.notifyObservers();
    }

    public void addTrainTokensToPlayer(Player player, int tokens){
        ArrayList<Player> players = currentGameState.getPlayers();
        for(int i = 0; i < players.size(); i++){

            Player p = players.get(i);
            if(p.getName().equals(player.getName())) {

                p.addTrainTokens(tokens);
                if (p.getName().equals(currentPlayer.getName())) {
                    currentPlayer.addTrainTokens(tokens);
                }
            }
        }
        currentPlayer.notifyObservers();
        currentGameState.notifyObservers();
    }

    public void subtractTrainTokensFromPlayer(Player player, int tokens){
        ArrayList<Player> players = currentGameState.getPlayers();
        for(int i = 0; i < players.size(); i++){

            Player p = players.get(i);
            if(p.getName().equals(player.getName())) {

                p.subtractTrainTokens(tokens);
                if (p.getName().equals(currentPlayer.getName())) {
                    currentPlayer.subtractTrainTokens(tokens);
                }
            }
        }
        currentPlayer.notifyObservers();
        currentGameState.notifyObservers();
    }

    public void addTrainCardToPlayer(Player player, TrainCard card){
        ArrayList<Player> players = currentGameState.getPlayers();
        for(int i = 0; i < players.size(); i++){

            Player p = players.get(i);
            if(p.getName().equals(player.getName())) {

                p.addTrainCard(card);
                if (p.getName().equals(currentPlayer.getName())) {
                    currentPlayer.addTrainCard(card);
                }
            }
        }
        currentPlayer.notifyObservers();
        currentGameState.notifyObservers();
    }

    public void removeTrainCardFromPlayer(Player player, TrainCard card) throws Exception{
        ArrayList<Player> players = currentGameState.getPlayers();
        for(int i = 0; i < players.size(); i++){

            Player p = players.get(i);
            if(p.getName().equals(player.getName())) {

                p.removeTrainCard(card.getColor());
                if (p.getName().equals(currentPlayer.getName())) {
                    currentPlayer.removeTrainCard(card.getColor());
                }
            }
        }
        currentPlayer.notifyObservers();
        currentGameState.notifyObservers();
    }

    public void addDestinationCardToPlayer(Player player, DestinationCard card){
        ArrayList<Player> players = currentGameState.getPlayers();
        for(int i = 0; i < players.size(); i++){

            Player p = players.get(i);
            ArrayList<DestinationCard> cards = new ArrayList<>();
            cards.add(card);
            if(p.getName().equals(player.getName())) {

                p.addDestinationCards(cards);
                if (p.getName().equals(currentPlayer.getName())) {
                    currentPlayer.addDestinationCards(cards);
                }
            }
        }
        currentPlayer.notifyObservers();
        currentGameState.notifyObservers();
    }

    public void removeDestinationCardFromPlayer(Player player, DestinationCard card) {
        ArrayList<Player> players = currentGameState.getPlayers();
        for (int i = 0; i < players.size(); i++) {

            Player p = players.get(i);
            ArrayList<DestinationCard> cards = new ArrayList<>();
            cards.add(card);
            if (p.getName().equals(player.getName())) {

                p.removeDestinationCards(cards);
                if (p.getName().equals(currentPlayer.getName())) {
                    currentPlayer.removeDestinationCards(cards);
                }
            }
        }
        currentPlayer.notifyObservers();
        currentGameState.notifyObservers();
    }
}
