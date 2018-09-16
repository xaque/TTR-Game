package server;

import shared.CommandData;
import shared.CommandType;
import shared.Results;

public class ToLowerCaseHandler extends HandlerBase {
    public ToLowerCaseHandler(){}

    @Override
    public Results runCommand(CommandData cd) {
        if (cd.getType() != CommandType.TOLOWERCASE){
            throw new RuntimeException("CommandType mismatch.");
        }
        String s = StringProcessor.getInstance().toLowerCase(cd.getData());
        return new Results(true, s, "");
    }
}
