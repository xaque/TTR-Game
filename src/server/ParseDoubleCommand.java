package server;

import shared.CommandData;
import shared.CommandType;
import shared.Results;

public class ParseDoubleCommand implements iCommand {

    private CommandData cd;

    public ParseDoubleCommand(CommandData cd){
        this.cd = cd;
    }

    /**
     * Execute iCommand
     * Parse double using the StringProcessor, and create response including any exceptions
     * @return The Results of the operation to be sent back to client
     */
    @Override
    public Results execute() {
        if (cd.getType() != CommandType.PARSEDOUBLE){
            throw new RuntimeException("CommandType mismatch.");
        }
        try{
            Double d = StringProcessor.getInstance().parseDouble(cd.getData());
            return new Results(true, d, "");
        }
        catch (NumberFormatException e){
            return new Results(false, null, e.getMessage());
        }
    }
}
