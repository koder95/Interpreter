# Wzorzec projektowy Interpreter
[![pl](https://img.shields.io/badge/lang-pl-ff0000)](https://github.com/koder95/Interpreter/blob/master/README.md)
[![en](https://img.shields.io/badge/lang-en-blue)](https://github.com/koder95/Interpreter/blob/master/README.en.md)

[![Java CI with Maven](https://github.com/koder95/Interpreter/actions/workflows/maven.yml/badge.svg)](https://github.com/koder95/Interpreter/actions/workflows/maven.yml)

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
ale również implementacje interfejsu [parsera](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Parser.java),
który wykorzystywany jest do budowania drzewa abstrakcyjnej syntaktyki za pomocą metody
[buildAbstractSyntaxTree(java.util.Queue)](https://github.com/koder95/Interpreter/blob/2d2197598c8597dac693b7520f52d9bac62c9ade/src/main/java/pl/koder95/interpreter/Parser.java#L23).

## Zależność Maven
```xml
<dependency>
    <groupId>pl.koder95</groupId>
    <artifactId>interpreter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Przykład
```java
package pl.koder95.interpreter;

import java.io.StringReader;
import java.util.*;

/**
 * Klasa zawierająca przykładową implementację interpretera.
 * Zaimplementowany język jest kontekstowy i określa sortowanie ciągów znaków za pomocą instrukcji
 * {@code ORDER BY ASC} lub {@code ORDER BY DSC}, albo {@code ORDER BY REV}.
 * Dla pewnego porządku: język nazywa się {@code SortExaL} od
 * ang. <i><b>Sort</b>ing <b>Exa</b>mple <b>L</b>anguage</i> (przykładowy język sortowania).
 */
public final class Example {

    private Example() {}

    /**
     * Metoda interpretująca źródło znaków i wprowadzone linie.
     * @param readable źródło znaków do zinterpretowania
     * @param lines tablica linii umieszczonych w kontekście przed procesem interpretacji
     * @return lista linii posortowanych zgodnie z instrukcjami
     */
    public static List<String> interpret(Readable readable, String... lines) {
        // Poniższy przykład pokazuje sposób korzystania z interpretera.
        // Pierwszym krokiem jest utworzenie obiektu klienta:
        ExampleClient client = new ExampleClient();
        // Należy stworzyć najpierw instancję kontekstu, który będzie wykorzystywany przez interpreter:
        ExampleContext context = new ExampleContext();
        if (lines.length > 0) {// – sprawdzanie, czy są jakieś linie we wprowadzonej tablicy
            context.lines.addAll(List.of(lines)); // – linie dodawane są do kontekstu
        }
        // Aby utworzyć nową instancję interpretera, należy to zrobić za pośrednictwem klienta:
        ExampleInterpreter interpreter = client.newInterpreter(context);
        // Interpreter dokonuje interpretacji źródła znaków i zwraca wynik w postaci listy linii:
        return interpreter.interpret(readable);
    }

    public static void main(String[] args) {
        // Przykładowe instrukcje:
        String expressions = """
                B
                D
                C
                ORDER BY DSC
                A
                ORDER BY REV
                E
                ORDER BY REV
                ORDER BY REV
                F
                """;
        StringReader readable = new StringReader(expressions);
        // Interpreter dokonuje interpretacji i zwraca wynik w postaci listy linii:
        List<String> lines = args.length > 0? interpret(readable, String.join(" ", args)) : interpret(readable);
        // Drukowanie wyników interpretacji:
        System.out.println(lines); // Spodziewany print (jeśli nie było argumentów): [A, B, C, D, E, F]
    }

/*
    IMPLEMENTACJE
    =============
    * ExampleContext – implementuje przykładowy kontekst dla języka kontekstowego
    * Line – implementuje wyrażenie nieterminalne reprezentujące linię tekstu
    * OrderByType – definiuje typy sposobów porządkowania linii tekstu
    * Sort – implementuje wyrażenie terminalne, które zwraca posortowane linie według podanego sposobu
    * Add – implementuje wyrażenie terminalne, które dodaje do kontekstu linię tekstu
    * Instructions – implementuje wyrażenie terminalne, które zawiera listę innych wyrażeń terminalnych
    * LineTokenizer – dokonuje tokenizacji dzieląc źródło znaków na linie
    * ExampleParser – implementuje parser, który tworzy AST (zwraca Instructions)
    * ExampleInterpreter – implementuje interpreter przez określenie statycznego parsera i zmiennego kontekstu
    * ExampleClient – implementuje klienta tworzącego interpreter

    LineTokenizer pobiera znaki i przekształca je w linie (Line).
    Parser (ExampleParser) analizuje linie i określa ich znaczenie.
    Linie są przekształcane przez parser na Add albo Sort, które dodawane są do listy instrukcji (Instructions).
    Interpreter (ExampleInterpreter) dokonuje interpretacji instrukcji i zwraca listę posortowanych linii.
    Klient zajmuje się utworzeniem interpretera, w zależności od kontekstu (w tym miejscu jest on ignorowany).
*/
    private static class ExampleContext implements Context {
        private final List<String> lines = new LinkedList<>();
    }

    private record Line(String content) implements NonTerminalExpression<String> {
        @Override
        public String getObject() {
            return content;
        }
    }

    private enum OrderByType {
        ASC, DSC, REV
    }

    private record Sort(OrderByType orderBy) implements TerminalExpression<ExampleContext, List<String>> {

        @Override
        public List<String> interpret(ExampleContext context) {
            if (orderBy == OrderByType.ASC) context.lines.sort(Comparator.naturalOrder());
            else if (orderBy == OrderByType.DSC) context.lines.sort(Comparator.reverseOrder());
            else if (orderBy == OrderByType.REV) Collections.reverse(context.lines);
            List<String> ordered = new ArrayList<>(context.lines);
            context.lines.clear();
            return ordered;
        }

        @Override
        public String asString() {
            return "ORDER BY " + orderBy.name();
        }
    }

    private record Add(Line line) implements TerminalExpression<ExampleContext, List<String>> {
        @Override
        public List<String> interpret(ExampleContext context) {
            if (line != null) context.lines.add(line.content);
            return context.lines;
        }

        @Override
        public String asString() {
            return line == null? "null" : line.content();
        }
    }

    private record Instructions(List<TerminalExpression<ExampleContext, List<String>>> terminalExpressionList)
            implements TerminalExpression<ExampleContext, List<String>> {
        @Override
        public List<String> interpret(ExampleContext context) {
            for (TerminalExpression<ExampleContext, List<String>> expr : terminalExpressionList) {
                List<String> interpreted = expr.interpret(context);
                if (expr instanceof Sort) {
                    context.lines.addAll(interpreted);
                }
            }
            return context.lines;
        }

        @Override
        public String asString() {
            return "INSTRUCTIONS";
        }
    }

    private static final class LinesTokenizer extends Tokenizer {
        private final Queue<Line> lineQueue = new LinkedList<>();
        private Scanner scanner = null;

        @Override
        public Line next() {
            while (scanner != null && scanner.hasNextLine()) {
                String content = scanner.nextLine();
                lineQueue.add(new Line(content));
            }
            return lineQueue.poll();
        }

        @Override
        public boolean hasNext() {
            return (scanner != null && scanner.hasNext()) || !lineQueue.isEmpty();
        }

        @Override
        public void setSource(Readable source) {
            super.setSource(source);
            scanner = new Scanner(source);
        }
    }

    private static final class ExampleParser implements Parser<ExampleContext, List<String>> {
        public static final Tokenizer TOKENIZER = new LinesTokenizer();

        @Override
        public Tokenizer getTokenizer() {
            return TOKENIZER;
        }

        @Override
        public TerminalExpression<ExampleContext, List<String>> buildAbstractSyntaxTree(Queue<NonTerminalExpression<?>> tokens) {
            List<TerminalExpression<ExampleContext, List<String>>> terminalExpressionList = new LinkedList<>();
            while (!tokens.isEmpty()) {
                NonTerminalExpression<?> token = tokens.poll();
                if (token instanceof Line line) {
                    String content = line.content();
                    String tag = "ORDER BY";
                    if (content.startsWith(tag)) {
                        Sort sorting = recognizeSort(content.substring(tag.length()).strip());
                        terminalExpressionList.add(sorting);
                    } else {
                        terminalExpressionList.add(new Add(line));
                    }
                } else {
                    terminalExpressionList.add(new Add(new Line(token.asString())));
                }
            }
            return new Instructions(terminalExpressionList);
        }

        private static Sort recognizeSort(String type) {
            Sort sorting;
            if (type.equals(OrderByType.ASC.name())) {
                sorting = new Sort(OrderByType.ASC);
            } else if (type.equals(OrderByType.DSC.name())) {
                sorting = new Sort(OrderByType.DSC);
            } else if (type.equals(OrderByType.REV.name())) {
                sorting = new Sort(OrderByType.REV);
            } else if (type.isBlank()) {
                sorting = new Sort(null);
            } else {
                throw new SyntaxException("Unknown ordering type");
            }
            return sorting;
        }
    }

    private record ExampleInterpreter(ExampleContext context) implements Interpreter<ExampleContext, List<String>> {
        public static final ExampleParser PARSER = new ExampleParser();

        @Override
        public ExampleContext getContext() {
            return context;
        }

        @Override
        public ExampleParser getParser() {
            return PARSER;
        }
    }

    private static final class ExampleClient implements Client<ExampleContext, List<String>, ExampleInterpreter> {
        @Override
        public ExampleInterpreter newInterpreter(ExampleContext context) {
            return new ExampleInterpreter(context);
        }
    }
}
```
