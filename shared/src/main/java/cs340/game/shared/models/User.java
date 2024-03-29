package cs340.game.shared.models;

import java.io.Serializable;
import java.util.Objects;

/**
 * A container for basic information about a user/player.
 */
public class User implements Serializable{

    private String username;
    private String password;
    private String authToken;

    public User(String u, String p){
        username = u;
        password = p;
        authToken = null;
    }

    public User(String u, String p, String a){
        username = u;
        password = p;
        authToken = a;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return (username.equals(user.username)) &&
                (password.equals(user.password));
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}