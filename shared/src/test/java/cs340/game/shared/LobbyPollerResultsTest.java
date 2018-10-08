package cs340.game.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs340.game.shared.models.GameList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LobbyPollerResultsTest {
    LobbyPollerResults lpr1;
    private boolean success1 = true;
    private GameList data1 = new GameList();
    private int sequenceNumber1 = 2323;
    private String errorInfo1 = "";

    @BeforeEach
    void setUp() {
        lpr1 = new LobbyPollerResults(success1, data1, errorInfo1);
        lpr1.setSequenceNumber(sequenceNumber1);
    }

    @Test
    void isSuccess() {
        assertEquals(success1, lpr1.isSuccess());
    }

    @Test
    void getData() {
        assertEquals(data1, lpr1.getData());
    }

    @Test
    void getSequenceNumber() {
        assertEquals(sequenceNumber1, lpr1.getSequenceNumber());
    }

    @Test
    void setSequenceNumber() {
        int newSeq = 35246;
        lpr1.setSequenceNumber(newSeq);
        assertEquals(newSeq, lpr1.getSequenceNumber());
    }

    @Test
    void getErrorInfo() {
        assertEquals(errorInfo1, lpr1.getErrorInfo());
    }
}