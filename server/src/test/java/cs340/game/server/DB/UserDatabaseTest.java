package cs340.game.server.DB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs340.game.shared.models.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDatabaseTest {
    User user;

    @BeforeEach
    void setUp() {
        user = new User("donovan", "sup3rp@$$w0rd");
    }

    @Test
    void getInstance() {
        assertNotNull(UserDatabase.getInstance());
    }

    @Test
    void addUser() {
        String auth = UserDatabase.getInstance().addUser(user);
        assertNotNull(auth);
    }

    @Test
    void containsUser() {
        UserDatabase.getInstance().addUser(user);
        assertTrue(UserDatabase.getInstance().containsUser(user));
    }
}