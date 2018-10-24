package cs340.game.shared.results;

import java.io.Serializable;

/**
 * Created by Stephen on 10/5/2018.
 */

public interface Results extends Serializable {
    boolean isSuccess();
    String getErrorInfo();
}
