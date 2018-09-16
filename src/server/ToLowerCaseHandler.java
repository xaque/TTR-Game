package server;

import shared.Results;

public class ToLowerCaseHandler extends HandlerBase {
    public ToLowerCaseHandler(){}

    @Override
    public Results runCommand(Object obj) {
        String s = StringProcessor.getInstance().toLowerCase((String) obj);//TODO careful bout this cast?
        return new Results(true, s, "");
    }
}
