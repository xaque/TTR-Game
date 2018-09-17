package server;

import shared.CommandData;
import shared.CommandType;
import shared.Results;

public class ParseDoubleCommand implements iCommand {

    private CommandData cd;

    public ParseDoubleCommand(CommandData cd){
        this.cd = cd;
    }

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
