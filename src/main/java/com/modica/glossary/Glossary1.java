package com.modica.glossary;

import java.util.*;

/**
 * {@code Glossary} represented as a {@link Map}.
 *
 * @author Matthew Modica
 */
public final class Glossary1 implements Glossary {

  /*
   * Private members
   * ------------------------------------------------------------------------
   */

  /**
   * Main representation of the {@link Glossary} object.
   * Stores the Glossary's terms and definitions as key-value pairs.
   */
  private Map<String, String> rep;

  /**
   * The number of entries in this {@link Glossary}.
   */
  private int size;

  /**
   * The text color of a term on that term's {@link Glossary} definition page.
   */
  private String termColor;

  /**
   * The title of this {@link Glossary}.
   */
  private String title;

  /**
   * Boolean determining whether the definition page will link terms
   * that appear in a definition to that term's HTML definition page.
   */
  private boolean isInNestedTermsMode;

  /**
   * Boolean determining whether the index page will display terms in
   * alphabetical order.
   */
  private boolean isInAlphabeticalOrderingMode;

  /**
   * Location of the background image for the HTML index page.
   */
  private String backgroundImage;

  /**
   * Creator of initial representation.
   */
  private void createNewRep() {
    this.rep = new HashMap<>();
    this.size = 0;
    this.title = "Glossary";
    this.termColor = "green";
    this.backgroundImage = "";
    this.isInNestedTermsMode = false;
    this.isInAlphabeticalOrderingMode = false;
  }

  /*
   * Constructor
   * ------------------------------------------------------------------------
   */

  /**
   * Constructor for an empty Glossary.
   */
  public Glossary1() {
    this.createNewRep();
  }

  /*
   * Instance methods
   * ------------------------------------------------------------------------
   */

  @Override
  public void addEntry(String term, String definition) {
    this.rep.entrySet().add(new AbstractMap.SimpleEntry<>(term, definition));
    this.size++;
  }

  @Override
  public String removeEntry(String term) {
    assert this.rep.containsKey(term) : "Violation of: term is in DOMAIN(this)";

    this.size--;
    return this.rep.remove(term);
  }

  @Override
  public String getDefinition(String term) {
    assert this.rep.containsKey(term) : "Violation of: term is in DOMAIN(this)";

    return this.rep.get(term);
  }

  @Override
  public boolean hasTerm(String term) {
    return this.rep.containsKey(term);
  }

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public void setTermColor(String termColor) {
    this.termColor = termColor;
  }

  @Override
  public String getTermColor() {
    return this.termColor;
  }

  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String getTitle() {
    return this.title;
  }

  @Override
  public void setNestedTermsMode(boolean nestedTerms) {
    this.isInNestedTermsMode = nestedTerms;
  }

  @Override
  public boolean isInNestedTermsMode() {
    return this.isInNestedTermsMode;
  }

  @Override
  public void setAlphabeticalOrderMode(boolean isAlphabetical) {
    this.isInAlphabeticalOrderingMode = isAlphabetical;
  }

  @Override
  public boolean isInAlphabeticalOrderMode() {
    return this.isInAlphabeticalOrderingMode;
  }

  @Override
  public void setBackgroundImage(String imageUrl) {
    this.backgroundImage = imageUrl;
  }

  @Override
  public String removeBackgroundImage() {
    String imageUrl = this.backgroundImage;
    this.backgroundImage = "";
    return imageUrl;
  }

  //TODO: Learn Java file reading
  @Override
  public void readText(String fileName) {
    this.rep.clear();
    SimpleReader in = new SimpleReader1L(fileName);

    while (!in.atEOS()) {
      String term = in.nextLine();

      StringBuilder defBuilder = new StringBuilder();
      String nextLine = in.nextLine();
      while (!nextLine.isEmpty()) {
        defBuilder.append(nextLine);
        nextLine = in.nextLine();
      }

      this.addEntry(term, defBuilder.toString());
    }

    in.close();
  }

  // TODO: Learn to write to files, Create outputHtmlSorted method???
  @Override
  public void outputHTML(String folderName) {
    SimpleWriter indexOut = new SimpleWriter1L(
        folderName + "/" + "index.html");
    SimpleWriter defOut = new SimpleWriter1L();
    List<String> termList = this.rep.keySet().stream().toList();
    /*
     * Create a set of characters that are considered separators
     */
    List<Character> separators = Arrays.asList('\t', '\n', '\r', '.', ',', '?',
        '!', ' ', ':', ';', '"', '-', '[', ']', '(', ')', '/', '\'');

    GlossaryUtilities.outputIndexHeader(indexOut, this.title, this.backgroundImage);

    while (termQ.length() > 0) {
      String term = termQ.dequeue();
      String definition = this.rep.value(term);

      indexOut.println(
          "<li><a href=\"" + term + ".html\">" + term + "</a></li>");

      /*
       * Print each definition into an HTML file named after the
       * corresponding term
       */
      defOut = new SimpleWriter1L(folderName + "/" + term + ".html");

      GlossaryUtilities.outputDefHeader(defOut, term,
          this.backgroundImage);

      /*
       * Set the term text color to the color specified by this.termColor
       * and print the term as an HTML heading
       */
      defOut.print("<h2><b><i><font color=\"" + this.termColor + "\">");
      defOut.print(term);
      defOut.println("</font></i></b></h2>");

      /*
       * If the glossary is in nested terms mode, print the definition
       * word-by-word, checking if each word is a term in the glossary. If
       * it is a term, link the word to that term's HTML page. Otherwise,
       * just print the definition with no linking.
       */
      if (this.isInNestedTermsMode) {
        defOut.print("<blockquote>");
        int position = 0;
        while (position < definition.length()) {
          String nextWordOrSeparator = GlossaryUtilities
              .nextWordOrSeparator(definition, position,
                  separators);

          if (this.rep.hasKey(nextWordOrSeparator)) {
            defOut.print("<a href=\"" + nextWordOrSeparator
                + ".html\">" + nextWordOrSeparator + "</a>");
          } else {
            defOut.print(nextWordOrSeparator);
          }

          position += nextWordOrSeparator.length();
        }
        defOut.println("</blockquote>");
      } else {
        defOut.println("<blockquote>" + definition + "</blockquote>");
      }

      GlossaryUtilities.outputDefFooter(defOut);
    }
    this.rep.clear();
    GlossaryUtilities.outputIndexFooter(indexOut);

    indexOut.close();
    defOut.close();
  }

  public void clear() {
    this.createNewRep();
  }

  public Glossary newInstance() {
    try {
      return this.getClass().getConstructor().newInstance();
    } catch (ReflectiveOperationException e) {
      throw new AssertionError(
          "Cannot construct object of type " + this.getClass());
    }
  }

  public void transferFrom(Glossary source) {
    assert source != null : "Violation of: source is not null";
    assert source != this : "Violation of: source is not this";
    assert source instanceof Glossary1 : ""
        + "Violation of: source is of dynamic type Glossary1";

    Glossary1 localSource = (Glossary1) source;
    this.rep = localSource.rep;
    localSource.createNewRep();
  }

  @Override
  public String toString() {
    StringBuilder stringRep = new StringBuilder();

    for (Map.Pair<String, String> pair : this.rep) {
      stringRep.append(pair.key() + " - \n" + pair.value() + "\n\n");

    }

    return stringRep.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (!(obj instanceof Glossary g)) {
      return false;
    }

    if (this.size() != g.size()) {
      return false;
    }
    /*
     * If this.rep contains any key not found in Glossary g, they are not
     * equal. If the definitions of those terms do not match, they are
     * not equal. Size is equal, so the inverse does not need to be checked.
     */
    for (Map.Pair<String, String> pair : this.rep) {
      String pairTerm = pair.key();
      if (g.hasTerm(pair.key())) {
        if (!g.getDefinition(pairTerm).equals(pair.value())) {
          return false;
        }
      } else {
        return false;
      }
    }
    /*
     * Objects are not null, are both instances of Glossary, and contain the
     * same (term, definition) pairs
     */
    return true;
  }

  @Override
  public int hashCode() {
    return this.rep.hashCode() + this.size();
  }
}
