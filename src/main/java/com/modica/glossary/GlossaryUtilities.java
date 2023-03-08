package com.modica.glossary;

import components.map.Map;
import components.queue.Queue;
import components.queue.Queue2;
import components.set.Set;
import components.simplewriter.SimpleWriter;
import java.util.Comparator;

/**
 * A utility class with useful methods for {@link Glossary}.
 *
 * @author Matthew Modica
 *
 *
 */
public final class GlossaryUtilities {

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
   * @param out
   *            The output stream
   * @param glossaryTitle
   *            The title of the HTML index page
   * @param backgroundImage
   *            The background image location for the HTML index page
   */
  public static void outputIndexHeader(SimpleWriter out, String glossaryTitle,
      String backgroundImage) {
    out.println("<html>");
    out.println("<head>");
    out.println("<title>" + glossaryTitle + "</title>");
    out.println("</head>");
    out.println("<body background=\"" + backgroundImage + "\">");
    out.println("<h2>" + glossaryTitle + "</h2>");
    out.println("<hr />");
    out.println("<h3>Index</h3>");
    out.println("<ul>");
  }

  /**
   * Output an HTML footer for the index page.
   *
   * @param out
   *            The output stream
   */
  public static void outputIndexFooter(SimpleWriter out) {
    out.println("</ul>");
    out.println("</body>");
    out.println("</html>");
  }

  /**
   * Output an HTML header for a definition page.
   *
   * @param out
   *            The output stream
   * @param term
   *            The corresponding term for the definition being printed
   * @param backgroundImage
   *            The background image location for the HTML definition pages
   */
  public static void outputDefHeader(SimpleWriter out, String term,
      String backgroundImage) {
    out.println("<html>");
    out.println("<head>");
    out.println("<title>" + term + "</title>");
    out.println("</head>");
    out.println("<body background=\"" + backgroundImage + "\">");
  }

  /**
   * Output an HTML footer for a definition page.
   *
   * @param out
   *            The output stream
   */
  public static void outputDefFooter(SimpleWriter out) {
    out.println("<hr />");
    out.println("<p>Return to <a href=\"index.html\">index</a>.</p>");
    out.println("</body>");
    out.println("</html>");
  }

  /**
   * Generate a queue of the keys in {@code map}. If sortAlphabetically ==
   * true, sort the queue using lexicographic ordering.
   *
   * @param map
   *            The map containing the keys to be enqueued
   * @param sortAlphabetically
   *            Whether to sort the queue alphabetically
   * @return a queue containing the keys from {@code map}
   */
  public static Queue<String> generateTermQueue(Map<String, String> map,
      boolean sortAlphabetically) {
    Queue<String> q = new Queue2<>();
    Comparator<String> cs = new StringLT();

    for (Map.Pair<String, String> pair : map) {
      q.enqueue(pair.key());
    }

    if (sortAlphabetically) {
      q.sort(cs);
    }

    return q;
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
      Set<Character> separators) {
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

}

