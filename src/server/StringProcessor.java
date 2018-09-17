package server;

import shared.iStringProcessor;

public final class StringProcessor implements iStringProcessor{

    private static final StringProcessor INSTANCE = new StringProcessor();

    private StringProcessor() {}

    public static StringProcessor getInstance(){
        return INSTANCE;
    }

    /**
     * Convert string to all lowercase characters
     * @param s The string to convert
     * @return the lower case string
     */
    @Override
    public String toLowerCase(String s) {
        return s.toLowerCase();
    }

    /**
     * Trim whitespace from the head and tail of a string
     * @param s The string to trim
     * @return The trimmed string
     */
    @Override
    public String trim(String s) {
        return s.trim();
    }

    /**
     * Parse a string to a double
     * @param s The string to convert
     * @return The parsed double
     */
    @Override
    public Double parseDouble(String s) {
        return Double.parseDouble(s);
    }
}
