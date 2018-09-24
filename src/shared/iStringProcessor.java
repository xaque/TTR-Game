package shared;

/**
 * The interface for implementing all valid CommandTypes
 */
public interface iStringProcessor {
    String toLowerCase(String s);
    String trim(String s);
    Double parseDouble(String s);
}
