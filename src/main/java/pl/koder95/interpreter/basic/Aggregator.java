package pl.koder95.interpreter.basic;

/**
 * Interfejs definiujący operację agregacji dwóch obiektów {@link Expression} w jeden obiekt {@link Expression}.
 */
public interface Aggregator {

    /**
     * Agreguje dwa obiekty {@link Expression} w jeden obiekt {@link Expression}.
     *
     * @param expr1 pierwszy obiekt {@link Expression} do agregacji.
     * @param expr2 drugi obiekt {@link Expression} do agregacji.
     * @return wynik agregacji jako obiekt {@link Expression}.
     * @throws IllegalArgumentException jeśli którykolwiek z argumentów nie jest obsługiwanym typem.
     */
    Expression aggregate(Expression expr1, Expression expr2);
}