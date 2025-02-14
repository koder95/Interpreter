package pl.koder95.interpreter;

import java.util.Objects;

/**
 * Wyrażenie nieterminalne to takie, któremu nie można przypisać żadnego znaczenia, ale jest podstawowym elementem,
 * z którego zbudowane mogą być {@link TerminalExpression wyrażenia terminalne}. Jest to odpowiednik tokena lub symbolu.
 * @param <T> typ obiektu, który może zostać skojarzony z wyrażeniem
 */
public non-sealed interface NonTerminalExpression<T> extends Expression {
    /**
     * Zwraca obiekt skojarzony z wyrażeniem, czyli taki, który stanowi wartość wyrażenia, jego reprezentację
     * lub jego dookreślenie. Wykorzystywany jest podczas interpretowania
     * przez {@link TerminalExpression wyrażenia terminalne}.
     * @return obiekt skojarzony z wyrażeniem
     */
    T getObject();

    @Override
    default String asString() {
        return Objects.toString(getObject());
    }
}
