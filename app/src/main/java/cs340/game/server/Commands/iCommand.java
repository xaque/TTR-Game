package cs340.game.server.Commands;

import cs340.game.shared.CommandData;
import cs340.game.shared.Results;
import cs340.game.shared.data.Data;

/**
 * Interface for command pattern implementation
 */
public interface iCommand {
    Results execute(Data data);
}
