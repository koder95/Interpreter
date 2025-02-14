package pl.koder95.interpreter;

/**
 * Interfejs dostarcza wyrażenie w formie ciągu znaków zwracany przez metodę {@code asString()}.
 * Ma dwie dopuszczalne odmiany, które różnią się ze względu na funkcje i znaczenie w procesie interpretacji.
 * <ol>
 *     <li>{@link NonTerminalExpression} – wyrażenie nieterminalne, które stanowi podstawowy element budowania innych typów wyrażeń</li>
 *     <li>{@link TerminalExpression} – wyrażenie terminalne, które można zinterpretować w ramach dostępnego kontekstu</li>
 * </ol>
 */
public sealed interface Expression permits TerminalExpression, NonTerminalExpression {

    /**
     * @return wyrażenie w postaci ciągu znaków
     */
    String asString();
}
