package server;

import shared.Results;

public class TrimHandler extends HandlerBase {
    public TrimHandler(){}

    @Override
    public Results runCommand(Object obj) {
        String s = StringProcessor.getInstance().trim((String) obj);
        return new Results(true, s, "");
    }
}
