package cs340.game.shared.results;

import java.io.Serializable;

public interface Results extends Serializable {
    boolean isSuccess();
    String getErrorInfo();
}
