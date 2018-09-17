package server;

import shared.CommandData;
import shared.CommandType;
import shared.Results;

public class TrimCommand implements iCommand {

    private CommandData cd;

    public TrimCommand(CommandData cd){
        this.cd = cd;
    }

    /**
     * Execute iCommand
     * Trim string whitespace using StringProcessor, the create response including any exceptions
     * @return The result of the operation to be sent back to client
     */
    @Override
    public Results execute() {
        if (cd.getType() != CommandType.TRIM){
            throw new RuntimeException("CommandType mismatch.");
        }
        String s = StringProcessor.getInstance().trim(cd.getData());
        return new Results(true, s, "");
    }
}
