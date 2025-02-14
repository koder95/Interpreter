package pl.koder95.interpreter;

/**
 * Wyrażenie terminalne to takie, które można zinterpretować, dlatego interfejs dostarcza
 * metodę zwracającą obiekt żądanego typu uzyskany w wyniku interpretacji.
 * @param <C> typ kontekstu wykorzystywanego podczas interpretowania wyrażenia
 * @param <R> typ obiektu zwracanego w wyniku interpretacji
 */
public non-sealed interface TerminalExpression<C extends Context, R> extends Expression {

    /**
     * Poddaje wyrażenie interpretacji, korzystając z podanego kontekstu.
     * @param context kontekst wykorzystywany podczas interpretowania wyrażenia
     * @return wynik interpretacji
     * @throws SyntaxException błąd składniowy wewnątrz wyrażenia
     */
    R interpret(C context);
}
