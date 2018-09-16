package client;

import shared.*;

public final class StringProcessorProxy implements iStringProcessor {

    private static final StringProcessorProxy INSTANCE = new StringProcessorProxy();

    private StringProcessorProxy() {}

    public static StringProcessorProxy getInstance(){
        return INSTANCE;
    }

    private Object validateResults(Results r){
        if (r.isSuccess()){
            return r.getData();
        }
        else{
            throw new NumberFormatException(r.getErrorInfo());
        }
    }

    //TODO more robust type casting
    @Override
    public String toLowerCase(String s) {
        CommandData cd = new CommandData(CommandType.TOLOWERCASE, s);
        Results r = ClientCommunicator.getInstance().send(CommonData.TOLOWERCASE_URI, cd);
        return (String) validateResults(r);
    }

    @Override
    public String trim(String s) {
        CommandData cd = new CommandData(CommandType.TRIM, s);
        Results r = ClientCommunicator.getInstance().send(CommonData.TRIM_URI, cd);
        return (String) validateResults(r);
    }

    @Override
    public Double parseDouble(String s) {
        CommandData cd = new CommandData(CommandType.PARSEDOUBLE, s);
        Results r = ClientCommunicator.getInstance().send(CommonData.PARSEDOUBLE_URI, cd);
        return (Double) validateResults(r);
    }
}
