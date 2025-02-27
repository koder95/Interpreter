package pl.koder95.interpreter.basic;

import java.util.Objects;
import pl.koder95.interpreter.NonTerminalExpression;

/**
 * Reprezentuje podstawowy (niezłożony) token zawierający ciąg znaków tej samej kategorii Unicode.
 * Klasa ta implementuje interfejs {@link pl.koder95.interpreter.NonTerminalExpression NonTerminalExpression} dla typu {@link String}.
 * Token jest uważany za "podstawowy", ponieważ zawiera znaki tylko jednej kategorii Unicode,
 * co odróżnia go od tokenów złożonych, które mogą zawierać znaki różnych kategorii.
 */
public class BasicUnicodeToken implements NonTerminalExpression<String> {
    private final String value;

    /**
     * Tworzy nowy podstawowy token z podaną wartością.
     *
     * @param value wartość tokenu jako ciąg znaków; nie może być {@code null}.
     * @throws NullPointerException jeśli wartość jest {@code null}.
     * @throws IllegalArgumentException jeśli ciąg znaków zawiera znaki różnych kategorii Unicode.
     */
    public BasicUnicodeToken(String value) {
        Objects.requireNonNull(value, "Value cannot be null");

        if (value.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be empty");
        }

        // Sprawdź, czy wszystkie znaki mają tę samą kategorię Unicode
        int firstCharType = Character.getType(value.charAt(0));
        for (int i = 1; i < value.length(); i++) {
            if (Character.getType(value.charAt(i)) != firstCharType) {
                throw new IllegalArgumentException(
                    "All characters in the value must be of the same Unicode category. " +
                    "Mismatch at position " + i + ": expected category " + firstCharType +
                    ", but found " + Character.getType(value.charAt(i))
                );
            }
        }

        this.value = value;
    }

    /**
     * Zwraca wartość tokenu jako ciąg znaków.
     *
     * @return wartość tokenu.
     */
    @Override
    public String getObject() {
        return value;
    }

    /**
     * Zwraca reprezentację tekstową tokenu. W tej implementacji jest to po prostu wartość tokenu.
     *
     * @return wartość tokenu jako ciąg znaków.
     */
    @Override
    public String asString() {
        return value;
    }

    /**
     * Zwraca kategorię Unicode dla pierwszego znaku w wartości tokenu.
     * Kategoria Unicode jest określana za pomocą metody {@link Character#getType(int)}.
     *
     * @return kategoria Unicode jako wartość całkowita.
     * @throws IllegalStateException jeśli wartość tokenu jest pusta.
     */
    public int getType() {
        if (value.isEmpty()) {
            throw new IllegalStateException("Value is empty, cannot determine type");
        }
        return Character.getType(value.charAt(0));
    }

    /**
     * Zwraca czytelny opis tokenu, zawierający jego wartość oraz kategorię Unicode.
     *
     * @return opis tokenu w formacie "Token: [wartość], Type: [kategoria]".
     */
    @Override
    public String toString() {
        return "Token: " + value + ", Type: " + getType();
    }
}