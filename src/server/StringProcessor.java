package server;

import shared.iStringProcessor;

public class StringProcessor implements iStringProcessor{

    private static final StringProcessor INSTANCE = new StringProcessor();

    private StringProcessor() {}

    public static StringProcessor getInstance(){
        return INSTANCE;
    }

    @Override
    public String toLowerCase(String s) {
        return s.toLowerCase();
    }

    @Override
    public String trim(String s) {
        return s.trim();
    }

    @Override
    public Double parseDouble(String s) {
        return Double.parseDouble(s);
    }
}
