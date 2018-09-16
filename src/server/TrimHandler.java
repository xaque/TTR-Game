package server;

import shared.CommandData;
import shared.CommandType;
import shared.Results;

public class TrimHandler extends HandlerBase {
    public TrimHandler(){}

    @Override
    public Results runCommand(CommandData cd) {
        if (cd.getType() != CommandType.TRIM){
            throw new RuntimeException("CommandType mismatch.");
        }
        String s = StringProcessor.getInstance().trim(cd.getData());
        return new Results(true, s, "");
    }
}
