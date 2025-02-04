package pl.koder95.interpreter;

import java.util.Objects;

public non-sealed interface NonTerminalExpression<T> extends Expression {
    T getObject();

    @Override
    default String asString() {
        return Objects.toString(getObject());
    }
}
