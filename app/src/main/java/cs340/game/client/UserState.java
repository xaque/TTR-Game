package cs340.game.client;

/**
 * Used to track the current state of the user. Whether they are logged out, logged in and in the
 * game lobby, or currently playing a game.
 */
public enum UserState {
    LOGGED_OUT, IN_LOBBY, IN_GAME
}
