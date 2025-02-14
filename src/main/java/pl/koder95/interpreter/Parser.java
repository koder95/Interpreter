package pl.koder95.interpreter;

import java.util.Queue;

/**
 * Interfejs dostarczający metodę, która buduje drzewo abstrakcyjnej syntaktyki.
 * @param <C> typ {@link Context kontekstu} dla {@link TerminalExpression wyrażenia terminalnego}
 * @param <R> typ obiektu zwracanego przez interpreter
 */
public interface Parser<C extends Context, R> {

    /**
     * Buduje drzewo abstrakcyjnej syntaktyki.
     * @param tokens dane wejściowe poddane tokenizacji
     * @return wyrażenie terminalne najwyższego poziomu
     * @throws SyntaxException w przypadku błędów składniowych w dostarczonych danych
     */
    TerminalExpression<C, R> buildAbstractSyntaxTree(Queue<NonTerminalExpression<?>> tokens);
}
