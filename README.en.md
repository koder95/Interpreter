# Interpreter Design Pattern
[![pl](https://img.shields.io/badge/lang-pl-ff0000)](https://github.com/koder95/Interpreter/blob/master/README.md)
[![en](https://img.shields.io/badge/lang-en-blue)](https://github.com/koder95/Interpreter/blob/master/README.en.md)

[![Java CI with Maven](https://github.com/koder95/Interpreter/actions/workflows/maven.yml/badge.svg)](https://github.com/koder95/Interpreter/actions/workflows/maven.yml)

This project is an implementation of the *Interpreter* design pattern in a universal version.

The [client](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Client.java) interface allows creating a new instance of the [interpreter](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Interpreter.java), which will operate in the given [context](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Context.java).

Formal languages can be divided into two groups based on context: context-free formal languages and context-sensitive formal languages. The context determines the final meaning of ambiguous [expressions](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Expression.java) from the perspective of only one expression. If each of the expressions is unambiguous and does not need clarification by the context, then an instance created by `new pl.koder95.interpreter.Context(){}` should be used, which means context-free grammar.

The [Interpreter](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Interpreter.java) interface not only provides a [method for interpreting read values](https://github.com/koder95/Interpreter/blob/b3f9b37f580d7580785ac4515601e07ce9b38d85/src/main/java/pl/koder95/interpreter/Interpreter.java#L38), but also implementations of the [parser](https://github.com/koder95/Interpreter/blob/master/src/main/java/pl/koder95/interpreter/Parser.java) interface, which is used to build an abstract syntax tree using the method [buildAbstractSyntaxTree(java.util.Queue)](https://github.com/koder95/Interpreter/blob/2d2197598c8597dac693b7520f52d9bac62c9ade/src/main/java/pl/koder95/interpreter/Parser.java#L23).

## Maven Dependency
```xml
<dependency>
    <groupId>pl.koder95</groupId>
    <artifactId>interpreter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Example
```java
package pl.koder95.interpreter;

import java.io.StringReader;
import java.util.*;

/**
 * Class containing an example implementation of the interpreter.
 * The implemented language is context-sensitive and defines sorting of strings using the instructions
 * {@code ORDER BY ASC}, {@code ORDER BY DSC}, or {@code ORDER BY REV}.
 * For some order: the language is called {@code SortExaL} from
 * the English <i><b>Sort</b>ing <b>Exa</b>mple <b>L</b>anguage</i>.
 */
public final class Example {

    private Example() {}

    /**
     * Method interpreting the source characters and input lines.
     * @param readable the source of characters to be interpreted
     * @param lines array of lines placed in the context before the interpretation process
     * @return list of lines sorted according to the instructions
     */
    public static List<String> interpret(Readable readable, String... lines) {
        // The following example shows how to use the interpreter.
        // The first step is to create a client object:
        ExampleClient client = new ExampleClient();
        // First, create an instance of the context that will be used by the interpreter:
        ExampleContext context = new ExampleContext();
        if (lines.length > 0) { // Check if there are any lines in the input array
            context.lines.addAll(List.of(lines)); // Add lines to the context
        }
        // To create a new instance of the interpreter, do it through the client:
        ExampleInterpreter interpreter = client.newInterpreter(context);
        // The interpreter interprets the source characters and returns the result as a list of lines:
        return interpreter.interpret(readable);
    }

    public static void main(String[] args) {
        // Example instructions:
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
        // The interpreter interprets and returns the result as a list of lines:
        List<String> lines = args.length > 0 ? interpret(readable, String.join(" ", args)) : interpret(readable);
        // Print the interpretation results:
        System.out.println(lines); // Expected output (if no arguments): [A, B, C, D, E, F]
    }

/*
    IMPLEMENTATIONS
    ================
    * ExampleContext – implements a sample context for a context-sensitive language
    * Line – implements a non-terminal expression representing a line of text
    * OrderByType – defines types of ways to order lines of text
    * Sort – implements a terminal expression that returns the sorted lines according to the specified way
    * Add – implements a terminal expression that adds a line of text to the context
    * Instructions – implements a terminal expression containing a list of other terminal expressions
    * LineTokenizer – tokenizes by dividing the source characters into lines
    * ExampleParser – implements a parser that creates AST (returns Instructions)
    * ExampleInterpreter – implements an interpreter by defining a static parser and a variable context
    * ExampleClient – implements a client creating the interpreter

    LineTokenizer takes characters and transforms them into lines (Line).
    The parser (ExampleParser) analyzes the lines and defines their meaning.
    Lines are transformed by the parser into Add or Sort, which are added to a list of instructions (Instructions).
    The interpreter (ExampleInterpreter) interprets the instructions and returns a list of sorted lines.
    The client deals with creating the interpreter, depending on the context (ignored here).
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
            return line == null ? "null" : line.content();
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
