package pl.koder95.interpreter;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Definiuje sposób podziału danych wejściowych na tokeny.
 * Standardowo korzysta ze {@link Scanner skanera}, aby dzielić i analizować ciąg odczytywanych wartości.
 * Nic nie stoi na przeszkodzie, aby użyć innego sposobu. Wystarczy oprócz implementacji metody {@link #next()}
 * nadpisać metodę {@link #hasNext()}.
 */
public abstract class Tokenizer {

    private Scanner scanner;

    /**
     * Tworzy nową instancję tokenizera ustawiając skaner na wartość {@code null}.
     * Dla poprawnego funkcjonowania należy wywołać metodę {@link #useScanner(Scanner)}.
     */
    public Tokenizer() {
        this.scanner = null;
    }

    /**
     * @param scanner skaner używany podczas procesu tokenizacji danych wejściowych
     */
    public void useScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Metoda używana podczas procesu tworzenia tokenów w metodzie {@link #next()}.
     * @return zwraca aktualnie ustawiony skaner
     */
    protected final Scanner getScanner() {
        return scanner;
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
    public boolean hasNext() {
        return scanner != null && scanner.hasNext();
    }

    /**
     * Przetwarza dane wejściowe i zwraca je w postaci {@link NonTerminalExpression wyrażenia nieterminalnego},
     * czyli tokenu.
     * @return token typu {@link NonTerminalExpression}
     * @throws SyntaxException w przypadku błędów składniowych w dostarczonych danych
     */
    public abstract NonTerminalExpression<?> next();
}
