package pl.koder95.interpreter;

public non-sealed interface NonTerminalExpression<C extends Context, R> extends Expression<C, R> {
    @Override
    default R interpret(C context) {
        throw new UnsupportedOperationException();
    }
}
