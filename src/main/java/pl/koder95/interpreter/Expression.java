package pl.koder95.interpreter;

public sealed interface Expression<C extends Context, R> permits TerminalExpression, NonTerminalExpression {

    R interpret(C context);
}
