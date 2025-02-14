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
     * @return {@link Tokenizer tokenizer} używany przez {@link Parser parser} do budowy drzewa składni
     */
    Tokenizer getTokenizer();
    /**
     * @return {@link Parser parser}, który pobiera tokeny, aby stworzyć drzewo składniowe
     */
    Parser<C, R> getParser();

    /**
     * @return fabryka {@link java.util.Scanner skanerów}, które standardowo pomagają w przetwarzaniu danych wejściowych
     */
    ScannerFactory getScannerFactory();

    /**
     * Dokonuje interpretacji danych wejściowych korzystając z {@link Context kontekstu}. Dane najpierw są tokenizowane
     * i zamieniane na {@link NonTerminalExpression wyrażenia nieterminalne}, aby zbudować z nich drzewo abstrakcyjnej
     * syntaktyki (AST).
     *
     * @param readable dane odczytywane przez tokenizer i zamieniane na postać terminalną
     * @return wynik interpretacji
     * @throws IllegalStateException w przypadku niedostarczenia parsera lub
     *                               w przypadku niedostarczenia tokenizera przez parser
     * @throws SyntaxException w przypadku błędów składniowych w dostarczonych danych
     */
    default R interpret(Readable readable) {
        Tokenizer tokenizer = getTokenizer();
        tokenizer.useScanner(getScannerFactory().create(readable));
        TerminalExpression<C, R> ast = getParser().buildAbstractSyntaxTree(tokenizer.enqueue());
        return ast.interpret(getContext());
    }
}
