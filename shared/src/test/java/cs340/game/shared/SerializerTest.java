package cs340.game.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import cs340.game.shared.data.Data;
import cs340.game.shared.data.LoginData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class SerializerTest {
    Data data;
    Results result;
    String serialized;

    @BeforeEach
    void setUp() {
        data = new LoginData(CommandType.LOGIN, "Donovan", "Sup3rS3cre+");
        result = new LoginResults(true, "a2689fee23", "");
    }

    @Test
    void serializeData() {
        try {
            serialized = Serializer.serializeData(data);
            if (serialized == null || serialized.equals("")){
                fail();
            }

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void serializeResults() {
        try {
            serialized = Serializer.serializeResults(result);
            if (serialized == null || serialized.equals("")){
                fail();
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deserializeCommandData() {
        //TODO is this method used?
    }

    @Test
    void deserializeData() {
        serializeData();
        try {
            Data d = Serializer.deserializeData(serialized);
            assertTrue(d instanceof LoginData);
            assertTrue(data instanceof LoginData);
            LoginData ld = (LoginData) d;
            LoginData loginData = (LoginData) data;
            assertEquals(loginData.getCommandType(), ld.getCommandType());
            assertEquals(loginData.getPassword(), ld.getPassword());
            assertEquals(loginData.getUsername(), ld.getUsername());

        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void deserializeResults() {
        serializeResults();
        try {
            Results r = Serializer.deserializeResults(serialized);
            //Fails asserting equal Results objects because they point to difference objects
            //TODO is there a way to assert equivalent in this case instead of equals
            assertEquals(result.isSuccess(), r.isSuccess());
            assertEquals(result.getErrorInfo(), r.getErrorInfo());
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }
}