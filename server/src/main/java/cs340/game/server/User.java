package cs340.game.server;

/**
 * Created by Stephen on 9/28/2018.
 */

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {return this.username;}

    public String getPassword() {return this.password;}
}
