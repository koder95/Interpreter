package pl.koder95.interpreter;

/**
 * Wyjątek wyrzucany w przypadku błędów syntaktycznych podczas działania interpretera.
 */
public class SyntaxException extends IllegalArgumentException {

    public SyntaxException() {
    }

    public SyntaxException(String message) {
        super(message);
    }

    public SyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public SyntaxException(Throwable cause) {
        super(cause);
    }
}
