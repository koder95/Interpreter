# Wzorzec projektowy Interpreter
Projekt jest implementacją wzorca projektowego *Interpreter* w wersji uniwersalnej.

Interfejs [klienta](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Client.java) pozwala na stworzenie nowej instancji
[interpretera](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Interpreter.java), który będzie działał w podanym
[kontekście](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Context.java).

Języki formalne można podzielić na dwie grupy ze względu na kontekst: języki formalne bezkontekstowe
i języki formalne kontekstowe. Kontekst określa ostateczne znaczenie
[wyrażeń](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Expression.java)
wieloznacznych w perspektywie tylko jednego wyrażenia. Jeśli każde z wyrażeń jest jednoznaczne i nie potrzebuje
uściśleń przez kontekst, wówczas należy używać instancji utworzonej przez
`new pl.koder95.interpreter.Context(){}`, co oznacza gramatykę bezkontekstową.

Interfejs [Interpreter](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Interpreter.java) dostarcza nie tylko
[metodę interpretującą odczytywane wartości](https://github.com/koder95/Interpreter/blob/b3f9b37f580d7580785ac4515601e07ce9b38d85/src/main/java/pl/koder95/interpreter/Interpreter.java#L38),
ale również implementacje interfejsów [parsera](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Parser.java),
[tokenizera](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Tokenizer.java)
oraz [fabryki skanerów](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/ScannerFactory.java),
które wykorzystywane są podczas budowania drzewa abstrakcyjnej syntaktyki za pomocą metod
[create(java.lang.Readable)](https://github.com/koder95/Interpreter/blob/b3f9b37f580d7580785ac4515601e07ce9b38d85/src/main/java/pl/koder95/interpreter/ScannerFactory.java#L19),
[buildAbstractSyntaxTree(java.util.Queue)](https://github.com/koder95/Interpreter/blob/b3f9b37f580d7580785ac4515601e07ce9b38d85/src/main/java/pl/koder95/interpreter/Parser.java#L17)
oraz [enqueue()](https://github.com/koder95/Interpreter/blob/b3f9b37f580d7580785ac4515601e07ce9b38d85/src/main/java/pl/koder95/interpreter/Tokenizer.java#L54).

## Zależność Maven
```xml
<dependency>
    <groupId>pl.koder95</groupId>
    <artifactId>Interpreter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```