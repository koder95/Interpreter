package pl.koder95.interpreter;

public interface Interpreter<C extends Context, R> {

    C getContext();

    Expression<C,R> buildAbstractSyntaxTree(String expression);

    default R interpret(String expression) {
        Expression<C, R> ast = buildAbstractSyntaxTree(expression);
        return ast.interpret(getContext());
    }
}
