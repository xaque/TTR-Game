package client;

import shared.CommonData;
import shared.iStringProcessor;
import shared.Results;

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
        Results r = ClientCommunicator.getInstance().send(CommonData.TOLOWERCASE_URI, s);
        return (String) validateResults(r);
    }

    @Override
    public String trim(String s) {
        Results r = ClientCommunicator.getInstance().send(CommonData.TRIM_URI, s);
        return (String) validateResults(r);
    }

    @Override
    public Double parseDouble(String s) {
        Results r = ClientCommunicator.getInstance().send(CommonData.PARSEDOUBLE_URI, s);
        return (Double) validateResults(r);
    }
}
