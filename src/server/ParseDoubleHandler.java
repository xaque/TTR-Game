package server;

import shared.CommandData;
import shared.CommandType;
import shared.Results;

public class ParseDoubleHandler extends HandlerBase{
    public ParseDoubleHandler(){}

    @Override
    public Results runCommand(CommandData cd) {
        if (cd.getType() != CommandType.PARSEDOUBLE){
            throw new RuntimeException("CommandType mismatch.");
        }
        try{
            Double d = StringProcessor.getInstance().parseDouble(cd.getData());
            return new Results(true, d, "");
        }
        catch (NumberFormatException e){
            return new Results(false, null, e.getMessage());//TODO is getMessage right for this case?
        }
    }
}
