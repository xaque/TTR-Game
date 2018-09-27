package cs340.game.server;

import cs340.game.shared.Results;

/**
 * Interface for command pattern implementation
 */
public interface iCommand {
    Results execute();
}
