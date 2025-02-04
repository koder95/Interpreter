package pl.koder95.interpreter;

/**
 * Interfejs klienta, który rozpoczyna tworzenie instancji {@link Interpreter interpretera} zależnego od podanego kontekstu.
 * @param <C> typ {@link Context kontekstu}, jaki obsługiwany jest przez interpreter
 * @param <R> typ obiektu zwracanego przez interpreter
 * @param <I> typ {@link Interpreter interpretera} zwracanego przez klienta
 */
public interface Client<C extends Context, R, I extends Interpreter<C, R>> {

    /**
     * Tworzy nową instancję {@link Interpreter interpretera}.
     * @param context {@link Context kontekst} obsługiwany przez utworzony interpreter
     * @return nowa instancja {@link Interpreter interpretera}
     */
    I newInterpreter(C context);
}
