package cs340.game.shared.models;

import java.io.Serializable;

public class User implements Serializable{

    private String username;
    private String password;
    private String authToken;

    public User(String u, String p){
        username = u;
        password = p;
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
}