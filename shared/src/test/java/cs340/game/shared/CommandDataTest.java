package cs340.game.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandDataTest {
    private CommandData cd1;
    private CommandData cd2;
    private String data1 = "fn2456cvq0896qw7ibocrei8bnotvhqw7";
    private String data2 = "paosufhdvpioguhawrelkjhasdviohnaresiohajsdfvlgkjar;ogij";
    private CommandType type1 = CommandType.LOGIN;
    private CommandType type2 = CommandType.CREATE_GAME;

    @BeforeEach
    void setUp(){
        cd1 = new CommandData(type1, data1);
        cd2 = new CommandData(type2, data2);
    }

    @Test
    void getType() {
        assertEquals(type1, cd1.getType());
        assertEquals(type2, cd2.getType());
    }

    @Test
    void getData() {
        assertEquals(data1, cd1.getData());
        assertEquals(type2, cd2.getType());
    }
}