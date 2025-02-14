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
     * @return {@link Parser parser}, który pobiera tokeny, aby stworzyć drzewo składniowe
     */
    Parser<C, R> getParser();

    /**
     * Dokonuje interpretacji danych wejściowych korzystając z {@link Context kontekstu}. Dane najpierw są tokenizowane
     * i zamieniane na {@link NonTerminalExpression wyrażenia nieterminalne}, aby zbudować z nich drzewo abstrakcyjnej
     * syntaktyki (AST).
     *
     * @param readable dane odczytywane przez tokenizer i zamieniane na postać terminalną
     * @return wynik interpretacji
     * @throws IllegalStateException w przypadku niedostarczenia parsera lub
     *                               w przypadku niedostarczenia tokenizera przez parser albo
     *                               w przypadku niedostarczenia fabryki skanerów
     * @throws SyntaxException w przypadku błędów składniowych w dostarczonych danych
     */
    default R interpret(Readable readable) {
        Parser<C, R> parser = getParser();
        if (parser == null) throw new IllegalStateException("Cannot use readable when parser is null", new NullPointerException());
        Tokenizer tokenizer = parser.getTokenizer();
        if (tokenizer != null) tokenizer.setSource(readable);
        else throw new IllegalStateException("Cannot use readable when tokenizer or scanner factory are null", new NullPointerException());
        TerminalExpression<C, R> ast = parser.buildAbstractSyntaxTree();
        return ast.interpret(getContext());
    }
}
