package pl.koder95.interpreter;

public sealed interface Expression permits TerminalExpression, NonTerminalExpression {

    String asString();
}
