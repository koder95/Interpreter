package pl.koder95.interpreter;

/**
 * Interfejs dostarcza metody do zbudowania drzewa abstrakcyjnej syntaktyki (AST) i interpretowania tej syntaktyki.
 *
 * @param <C> typ {@link Context kontekstu}
 * @param <R> typ obiektu zwracanego przez interpreter
 */
public interface Interpreter<C extends Context, R> {

    /**
     * @return kontekst używany podczas interpretacji wyrażeń terminalnych
     */
    C getContext();

    /**
     * Buduje drzewo abstrakcyjnej syntaktyki.
     * @param expression wyrażenie
     * @return wyrażenie terminalne najwyższego poziomu
     */
    TerminalExpression<C,R> buildAbstractSyntaxTree(String expression);

    /**
     * Dokonuje interpretacji danych wejściowych korzystając z {@link Context kontekstu}. Dane najpierw są tokenizowane
     * i zamieniane na {@link NonTerminalExpression wyrażenia nieterminalne}, aby zbudować z nich drzewo abstrakcyjnej
     * syntaktyki (AST).
     *
     * @return wynik interpretacji
     */
    default R interpret(String expression) {
        TerminalExpression<C, R> ast = buildAbstractSyntaxTree(expression);
        return ast.interpret(getContext());
    }
}
