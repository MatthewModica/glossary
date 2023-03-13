package com.modica.glossary;

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.io.IOException;

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

  private boolean sortAlphabetically;

  /**
   * Boolean determining whether the definition page will link terms
   * that appear in a definition to that term's HTML definition page.
   */
  private boolean isInNestedTermsMode;

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
    this.sortAlphabetically = false;
    this.isInNestedTermsMode = false;
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

  public void sortAlphabetically(boolean bool) {
    this.sortAlphabetically = bool;
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
  public void readText(String fileName) throws IOException {
    this.rep.clear();

    File inputFile = new File(fileName);
    Scanner inputReader = new Scanner(inputFile);

    while (inputReader.hasNext()) {
      String term = inputReader.nextLine();

      this.rep.entrySet()
              .add(new AbstractMap.SimpleEntry<>(term, GlossaryUtilities.readDefinition(inputReader)));
    }
  }

  @Override
  public void outputHTML(String folderName) throws IOException {

    List<String> termList = this.rep.keySet().stream().toList();

    File indexFile = new File("resources/index.html");
    FileWriter indexWriter = new FileWriter(folderName);

    GlossaryUtilities.writeIndexHeader(indexWriter, this.title, this.backgroundImage);
    if(this.sortAlphabetically) {
      GlossaryUtilities.writeIndexBodySorted(indexWriter, termList);
    } else {
      GlossaryUtilities.writeIndexBody(indexWriter, termList);
    }
    GlossaryUtilities.writeIndexFooter(indexWriter);

    for (String term : termList) {
      File definitionFile = new File("/resources/" + term);
      FileWriter definitionWriter = new FileWriter("/resources/" + term);
      String definition = this.rep.get(term);

      GlossaryUtilities.writeDefinitionHeader(definitionWriter, term,
          this.backgroundImage, this.termColor);
      if (this.isInNestedTermsMode) {
        GlossaryUtilities.writeDefinitionBodyNested(definitionWriter, definition, termList);
      } else {
        definitionWriter.write("<blockquote>" + definition + "</blockquote>");
      }
      GlossaryUtilities.writeDefinitionFooter(definitionWriter);
    }
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
    return this.rep.entrySet().stream().toList().toString();
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

    return this.toString().equals(g.toString());
    /*
     * Objects are not null, are both instances of Glossary, and contain the
     * same (term, definition) pairs
     */
  }

  @Override
  public int hashCode() {
    return this.rep.hashCode() + this.size();
  }
}
