/**
 * Paczka dostarcza implementację wzorca projektowego <i>Interpreter</i> w wersji uniwersalnej.
 * <p>
 * Interfejs {@link pl.koder95.interpreter.Client klienta} pozwala na stworzenie nowej instancji
 * {@link pl.koder95.interpreter.Interpreter interpretera}, który będzie działał w podanym
 * {@link pl.koder95.interpreter.Context kontekście}.
 * </p><p>
 * Języki formalne można podzielić na dwie grupy ze względu na kontekst: języki formalne bezkontekstowe
 * i języki formalne kontekstowe. Kontekst określa ostateczne znaczenie {@link pl.koder95.interpreter.Expression wyrażeń}
 * wieloznacznych w perspektywie tylko jednego wyrażenia. Jeśli każde z wyrażeń jest jednoznaczne i nie potrzebuje
 * uściśleń przez kontekst, wówczas należy używać instancji utworzonej przez
 * {@code new pl.koder95.interpreter.Context(){}}, co oznacza gramatykę bezkontekstową.
 * </p><p>
 * Interfejs {@link pl.koder95.interpreter.Interpreter} dostarcza nie tylko
 * {@link pl.koder95.interpreter.Interpreter#interpret(java.lang.Readable) metodę interpretującą odczytywane wartości},
 * ale również implementacje interfejsów {@link pl.koder95.interpreter.Parser parsera},
 * {@link pl.koder95.interpreter.Tokenizer tokenizera} oraz {@link pl.koder95.interpreter.ScannerFactory fabryki skanerów},
 * które wykorzystywane są podczas budowania drzewa abstrakcyjnej syntaktyki za pomocą metod
 * {@link pl.koder95.interpreter.ScannerFactory#create(java.lang.Readable) create(java.lang.Readable)},
 * {@link pl.koder95.interpreter.Parser#buildAbstractSyntaxTree(java.util.Queue) buildAbstractSyntaxTree(java.util.Queue)}
 * oraz {@link pl.koder95.interpreter.Tokenizer#enqueue() enqueue()}.
 * </p>
 * @see pl.koder95.interpreter.Client
 * @see pl.koder95.interpreter.Context
 * @see pl.koder95.interpreter.Expression
 * @see pl.koder95.interpreter.Interpreter
 * @see pl.koder95.interpreter.Parser
 * @see pl.koder95.interpreter.ScannerFactory
 * @see pl.koder95.interpreter.Tokenizer
 */
package pl.koder95.interpreter;