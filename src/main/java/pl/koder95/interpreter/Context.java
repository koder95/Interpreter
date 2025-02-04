package pl.koder95.interpreter;

/**
 * Interfejs kontekstu, w jakim interpretowane będą wyrażenia.
 * Języki formalne mogą mieć gramatykę niekontekstową lub kontekstową. Z tego powodu ten interfejs nie posiada żadnych
 * metod do implementacji i idealnie nadaje się do zastosowania w gramatykach bezkontekstowych w aktualnej formie.
 * Rozszerzenie interfejsu z dodaniem własnych metod umożliwi wykorzystanie go w gramatykach kontekstowych.
 */
public interface Context {
}
