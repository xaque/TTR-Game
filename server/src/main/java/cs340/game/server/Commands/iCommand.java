package cs340.game.server.Commands;

import cs340.game.shared.Results;
import cs340.game.shared.data.Data;

/**
 * Interface for command pattern implementation
 */
public interface iCommand {
    /**
     * The command enacts its logic to update the DB objects.
     * @param data Data sent from the client to update databases
     * @return Results object, contains success boolean, potential error message, and any info to
     *          return to the client
     */
    Results execute(Data data);
}
