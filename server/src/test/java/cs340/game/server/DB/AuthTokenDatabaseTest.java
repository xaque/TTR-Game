package cs340.game.server.DB;

import org.junit.jupiter.api.Test;

import cs340.game.shared.ServerException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

class AuthTokenDatabaseTest {

    @Test
    void getInstance() {
        assertNotNull(AuthTokenDatabase.getInstance());
    }

    @Test
    void getAuthToken() {
        AuthTokenDatabase.getInstance().addUser("Donovan");
        try {
            String s = AuthTokenDatabase.getInstance().getAuthToken("Donovan");
            assertNotNull(s);
        } catch (ServerException e) {
            e.printStackTrace();
            fail();
        }
        try {
            AuthTokenDatabase.getInstance().getAuthToken("InvalidUser01928741");
            fail();
        } catch (ServerException e) {

        }
    }

    @Test
    void addUser() {
        AuthTokenDatabase.getInstance().addUser("Marcus");
        try {
            AuthTokenDatabase.getInstance().getAuthToken("Marcus");
        } catch (ServerException e) {
            e.printStackTrace();
            fail();
        }
    }
}