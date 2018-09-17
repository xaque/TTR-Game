package client;

import shared.*;

public final class StringProcessorProxy implements iStringProcessor {

    private static final StringProcessorProxy INSTANCE = new StringProcessorProxy();

    private StringProcessorProxy() {}

    public static StringProcessorProxy getInstance(){
        return INSTANCE;
    }

    /**
     * Check if result is valid
     * @param r Result object to check
     * @return The result data, if it is valid
     */
    private Object validateResults(Results r) {
        if (r.isSuccess()){
            return r.getData();
        }
        else{
            throw new RuntimeException(r.getErrorInfo());
        }
    }


    //TODO safer type casting
    /**
     * Proxy for String toLowerCase method
     * Create CommandData from input to send to server, then return the server's result
     * @param s The string to convert to lower case
     * @return The lower case string
     */
    @Override
    public String toLowerCase(String s) {
        CommandData cd = new CommandData(CommandType.TOLOWERCASE, s);
        Results r = ClientCommunicator.getInstance().send(CommonData.TOLOWERCASE_URI, cd);
        return (String) validateResults(r);
    }

    /**
     * Proxy for String trim method
     * Create CommandData from input to send to server, then return the server's result
     * @param s The string to trim
     * @return the trimmed string
     */
    @Override
    public String trim(String s) {
        CommandData cd = new CommandData(CommandType.TRIM, s);
        Results r = ClientCommunicator.getInstance().send(CommonData.TRIM_URI, cd);
        return (String) validateResults(r);
    }

    /**
     * Proxy for Double parseDouble method
     * Create CommandData from input to send to server, then return the server's result
     * @param s The string to parse
     * @return The parsed Double
     */
    @Override
    public Double parseDouble(String s) {
        CommandData cd = new CommandData(CommandType.PARSEDOUBLE, s);
        Results r = ClientCommunicator.getInstance().send(CommonData.PARSEDOUBLE_URI, cd);
        if (!r.isSuccess()){
            throw new NumberFormatException(r.getErrorInfo());
        }
        return (Double) r.getData();
    }
}
