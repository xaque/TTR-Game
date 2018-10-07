package cs340.game.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginResultsTest {
    LoginResults lr1;
    LoginResults lr2;
    private boolean success1 = true;
    private String authToken1 = "a6f29c0121bd43";
    private String errorInfo1 = "";
    private boolean success2 = false;
    private String authToken2 = "977ff32c";
    private String errorInfo2 = "Invalid";

    @BeforeEach
    void setUp() {
        lr1 = new LoginResults(success1, authToken1, errorInfo1);
        lr2 = new LoginResults(success2, authToken2, errorInfo2);
    }

    @Test
    void isSuccess() {
        assertEquals(success1, lr1.isSuccess());
        assertEquals(success2, lr2.isSuccess());
    }

    @Test
    void getAuthToken() {
        assertEquals(authToken1, lr1.getAuthToken());
        assertEquals(authToken2, lr2.getAuthToken());
    }

    @Test
    void getErrorInfo() {
        assertEquals(errorInfo1, lr1.getErrorInfo());
        assertEquals(errorInfo2, lr2.getErrorInfo());
    }
}