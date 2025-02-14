package pl.koder95.interpreter;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Definiuje sposób podziału danych wejściowych na tokeny.
 * Korzysta ze {@link Readable źródła znaków}, aby dostarczać ciąg odczytywanych znaków.
 */
public abstract class Tokenizer {

    private Readable source;

    /**
     * Tworzy nową instancję tokenizera ustawiając źródło znaków na wartość {@code null}.
     * Dla poprawnego funkcjonowania należy wywołać metodę {@link #setSource(Readable)}.
     */
    public Tokenizer() {
        this.source = null;
    }

    /**
     * @param source źródło znaków wczytywanych podczas procesu tokenizacji
     */
    public void setSource(Readable source) {
        this.source = source;
    }

    /**
     * Metoda używana podczas procesu tworzenia tokenów w metodzie {@link #next()}.
     * @return zwraca aktualnie ustawione źródło
     */
    protected final Readable getSource() {
        return source;
    }

    /**
     * Umieszcza w kolejce wszystkie możliwe do odczytania tokeny.
     * @param queue kolejka wyrażeń nieterminalnych (tokenów)
     * @return {@code queue}
     * @throws SyntaxException w przypadku błędów składniowych w dostarczonych danych
     */
    public final Queue<NonTerminalExpression<?>> enqueue(Queue<NonTerminalExpression<?>> queue) {
        while (hasNext()) queue.add(next());
        return queue;
    }

    /**
     * Tworzy nową kolejkę wyrażeń nieterminalnych (tokenów) i umieszcza w niej odczytane tokeny z wejścia.
     * @return nowa instancja kolejki {@link NonTerminalExpression wyrażeń nieterminalnych (tokenów)}
     * @throws SyntaxException w przypadku błędów składniowych w dostarczonych danych
     */
    public Queue<NonTerminalExpression<?>> enqueue() {
        return enqueue(new LinkedBlockingQueue<>());
    }

    /**
     * Sprawdza, czy istnieje możliwość pobrania następnego tokenu.
     * @return {@code true} – jeśli istnieje następny token, {@code false} w przeciwnym razie
     */
    public abstract boolean hasNext();

    /**
     * Przetwarza dane wejściowe i zwraca je w postaci {@link NonTerminalExpression wyrażenia nieterminalnego},
     * czyli tokenu.
     * @return token typu {@link NonTerminalExpression}
     * @throws SyntaxException w przypadku błędów składniowych w dostarczonych danych
     */
    public abstract NonTerminalExpression<?> next();
}
