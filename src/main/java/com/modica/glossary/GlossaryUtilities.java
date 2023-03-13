package com.modica.glossary;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * A utility class with useful methods for {@link Glossary}.
 *
 * @author Matthew Modica
 *
 *
 */
public final class GlossaryUtilities {

  @FunctionalInterface
  public interface ThrowingConsumer<T, E extends Exception> {
    void accept(T t) throws E;
  }

  static <T> Consumer<T> throwingConsumerWrapper(
      ThrowingConsumer<T, Exception> throwingConsumer) {

    return i -> {
      try {
        throwingConsumer.accept(i);
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    };
  }

  /**
   * Comparator for comparing strings by lexicographic ordering.
   */
  private static class StringLT implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
      return o1.compareTo(o2);
    }

  }

  /**
   * Private constructor so this utility class cannot be instantiated.
   */
  private GlossaryUtilities() {
  }

  /**
   * Output an HTML header for the index page.
   *
   * @param indexWriter
   *            The output stream
   * @param glossaryTitle
   *            The title of the HTML index page
   * @param backgroundImage
   *            The background image location for the HTML index page
   */
  public static void writeIndexHeader(FileWriter indexWriter, String glossaryTitle,
      String backgroundImage) throws IOException {

    indexWriter.write("<html>");
    indexWriter.write("<head>");
    indexWriter.write("<title>" + glossaryTitle + "</title>");
    indexWriter.write("</head>");
    indexWriter.write("<body background=\"" + backgroundImage + "\">");
    indexWriter.write("<h2>" + glossaryTitle + "</h2>");
    indexWriter.write("<hr />");
    indexWriter.write("<h3>Index</h3>");
    indexWriter.write("<ul>");
  }

  public static void writeIndexBody(FileWriter indexWriter, List<String> termList)
      throws IOException {
    termList.forEach(throwingConsumerWrapper(term ->
        indexWriter.write("<li><a href=\"" + term + ".html\">" + term + "</a></li>")));
  }

  public static void writeIndexBodySorted(FileWriter indexWriter, List<String> termList)
      throws IOException {
    termList.stream()
            .sorted(String::compareToIgnoreCase)
            .forEach(throwingConsumerWrapper(term ->
                indexWriter.write("<li><a href=\"" + term + ".html\">" + term + "</a></li>")));

  }

  /**
   * Output an HTML footer for the index page.
   *
   * @param indexWriter
   *            The output stream
   */
  public static void writeIndexFooter(FileWriter indexWriter) throws IOException {
    indexWriter.write("</ul>");
    indexWriter.write("</body>");
    indexWriter.write("</html>");
    indexWriter.close();
  }

  /**
   * Output an HTML header for a definition page.
   *
   *
   *            The output stream
   * @param term
   *            The corresponding term for the definition being printed
   * @param backgroundImage
   *            The background image location for the HTML definition pages
   */
  public static void writeDefinitionHeader(FileWriter definitionWriter, String term,
      String backgroundImage, String termColor) throws IOException {
    definitionWriter.write("<html>");
    definitionWriter.write("<head>");
    definitionWriter.write("<title>" + term + "</title>");
    definitionWriter.write("</head>");
    definitionWriter.write("<body background=\"" + backgroundImage + "\">");
    definitionWriter.write("<h2><b><i><font color=\"" + termColor + "\">");
    definitionWriter.write(term);
    definitionWriter.write("</font></i></b></h2>");
  }

  public static void writeDefinitionBodyNested(FileWriter definitionWriter,
      String definition, List<String> termList) throws IOException {
    final List<Character> separatorList = Arrays.asList('\t', '\n', '\r', '.', ',', '?',
        '!', ' ', ':', ';', '"', '-', '[', ']', '(', ')', '/', '\'');

    definitionWriter.write("<definition>");

    int position = 0;
    while (position < definition.length()) {
      String nextWordOrSeparator = nextWordOrSeparator(definition, position, separatorList);
      if (termList.contains(nextWordOrSeparator)) {
        definitionWriter.write("<a href=\"" + nextWordOrSeparator + ".html\">" + nextWordOrSeparator + "</a>");
      } else {
        definitionWriter.write(nextWordOrSeparator);
      }
      position += nextWordOrSeparator.length();
    }

    definitionWriter.write("</blockquote>");
  }

  /**
   * Output an HTML footer for a definition page.
   *
   *
   *            The output stream
   */
  public static void writeDefinitionFooter(FileWriter definitionWriter) throws IOException {
    definitionWriter.write("<hr />");
    definitionWriter.write("<p>Return to <a href=\"index.html\">index</a>.</p>");
    definitionWriter.write("</body>");
    definitionWriter.write("</html>");
    definitionWriter.close();
  }

  /**
   * Returns the first "word" (maximal length string of characters not in
   * {@code separators}) or "separator string" (maximal length string of
   * characters in {@code separators}) in the given {@code definition}
   * starting at the given {@code position}.
   *
   * @param definition
   *            the {@code String} from which to get the word or separator
   *            string
   * @param position
   *            the starting index
   * @param separators
   *            the {@code Set} of separator characters
   * @return the first word or separator string found in {@code text} starting
   *         at index {@code position}
   */
  public static String nextWordOrSeparator(String definition, int position,
      List<Character> separators) {
    StringBuilder nextWordOrSeparator = new StringBuilder();
    int currentPos = position;
    char frontChar = definition.charAt(currentPos);

    /*
     * If the first character is not a separator, it must belong to a word.
     * Build the word string up until the next separator or the end of the
     * string. If the first character is a separator, return just that
     * separator.
     */
    if (!separators.contains(frontChar)) {
      while (currentPos < definition.length()
          && !separators.contains(definition.charAt(currentPos))) {
        nextWordOrSeparator.append(definition.charAt(currentPos));
        currentPos++;
      }
    } else {
      nextWordOrSeparator.append(frontChar);
    }

    return nextWordOrSeparator.toString();
  }

  public static String readDefinition(Scanner reader) {
    StringBuilder definition = new StringBuilder();

    String nextLine;
    while (!((nextLine = reader.nextLine()).isEmpty())) {
      definition.append(nextLine);
    }

    return definition.toString();
  }
}

