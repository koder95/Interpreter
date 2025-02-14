package pl.koder95.interpreter;

/**
 * Wyjątek wyrzucany w przypadku błędów syntaktycznych podczas działania interpretera.
 */
public class SyntaxException extends IllegalArgumentException {

    /**
     * Tworzy ogólny wyjątek.
     */
    public SyntaxException() {
    }

    /**
     * Tworzy wyjątek, który przekazuje wiadomość wyjaśniającą jego wyrzucenie.
     *
     * @param message wiadomość wyjaśniająca wyrzucenie wyjątku
     */
    public SyntaxException(String message) {
        super(message);
    }

    /**
     * Tworzy wyjątek, który przekazuje wiadomość wyjaśniającą jego wyrzucenie oraz powód wyrzucenia.
     *
     * @param message wiadomość wyjaśniająca wyrzucenie wyjątku
     * @param cause powód wyrzucenia wyjątku
     */
    public SyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Tworzy wyjątek, który przekazuje powód jego wyrzucenia.
     *
     * @param cause powód wyrzucenia wyjątku
     */
    public SyntaxException(Throwable cause) {
        super(cause);
    }
}
