package cs340.game.server;

import java.util.Objects;

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
}
