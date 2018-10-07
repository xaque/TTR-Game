package cs340.game.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LobbyResultsTest {
    private LobbyResults lr;
    private boolean success = true;
    private String errorInfo = "";

    @BeforeEach
    void setUp() {
        lr = new LobbyResults(success, errorInfo);
    }

    @Test
    void isSuccess() {
        assertEquals(success, lr.isSuccess());
    }

    @Test
    void getErrorInfo() {
        assertEquals(errorInfo, lr.getErrorInfo());
    }
}