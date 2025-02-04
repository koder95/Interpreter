package pl.koder95.interpreter;

/**
 * Interfejs klienta, który rozpoczyna tworzenie instancji {@link Interpreter interpretera} zależnego od podanego kontekstu.
 * @param <I> typ {@link Interpreter interpretera} zwracanego przez klienta
 * @param <C> typ {@link Context kontekstu}, jaki obsługiwany jest przez interpreter
 * @param <R> typ obiektu zwracanego przez interpreter
 */
public interface Client<I extends Interpreter<C, R>, C extends Context, R> {

    /**
     * Tworzy nową instancję {@link Interpreter interpretera}.
     * @param context {@link Context kontekst} obsługiwany przez utworzony interpreter
     * @return nowa instancja {@link Interpreter interpretera}
     */
    I newInterpreter(C context);
}
