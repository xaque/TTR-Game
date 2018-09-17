package server;

import shared.CommandData;
import shared.CommandType;
import shared.Results;

public class ToLowerCaseCommand implements iCommand {
    private CommandData cd;
    public ToLowerCaseCommand(CommandData cd){
        this.cd = cd;
    }
    @Override
    public Results execute() {
        if (cd.getType() != CommandType.TOLOWERCASE){
            throw new RuntimeException("CommandType mismatch.");
        }
        String s = StringProcessor.getInstance().toLowerCase(cd.getData());
        return new Results(true, s, "");
    }
}
