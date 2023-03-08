package com.modica.glossary;

import components.standard.Standard;

/**
 * Glossary kernel component with primary methods.
 *
 * @author Matthew Modica
 *
 */
public interface GlossaryKernel extends Standard<Glossary> {

  /**
   * Adds the pair (term, definition) to this Glossary.
   *
   * @param term
   *            the term to be added to this {@link Glossary}
   * @param definition
   *            the associated definition to be added
   */
  void addEntry(String term, String definition);

  /**
   * Removes the pair (term, definition) associated with and
   * returns the String term.
   *
   * @param term
   *            the term to be added to the glossary
   * @return the pair removed
   */
  String removeEntry(String term);

  /**
   * Reports whether {@code this} contains the term {@code term}.
   *
   * @param term
   *            The term to check against the terms contained in {@code this}
   * @return Whether term is in this Glossary
   */
  boolean hasTerm(String term);

  /**
   * Returns the definition associated with {@code term}.
   *
   * @param term
   *            the term associated with the desired definition
   * @return the definition associated with the given term
   */
  String getDefinition(String term);

  /**
   * Returns the term associated with {@code definition}.
   *
   * @param definition
   *            the definition associated with the desired term
   * @return the term associated with the given definition
   * @requires {@code definition} is in RANGE(this)
   */
  String getTerm(String definition);

  /**
   * Reports the size of {@code this}.
   *
   * @return the size of {@code this}.
   */
  int size();

  /**
   * Sets the text color of the term that appears at the top of the
   * corresponding definition's HTML page. Arguments may be either the text
   * string of an allowed HTML color, eg. "red", or a string hexadecimal
   * triplet representing a color, eg. "#FF0000". If the argument is not a
   * valid HTML color, the term will be displayed in black.
   *
   * @param termColor
   *            The color to set the text of the terms
   * @updates this
   */
  void setTermColor(String termColor);

  /**
   * Reports the text color of the term that appears at the top of the
   * corresponding definition's HTML page.
   *
   * @return The text color of the terms
   */
  String getTermColor();

  /**
   * Sets the title of {@code this}. This title will be displayed as the title
   * of generated HTML pages.
   *
   * @param title
   *            The title to set for {@code this}
   * @updates this
   */
  void setTitle(String title);

  /**
   * Reports the title of {@code this}.
   *
   * @return The title of {@code this}.
   */
  String getTitle();

  /**
   * Changes the mode of {@code this} to nested terms mode if
   * {@code nestedTerms} == true. Nested terms mode will link terms that
   * appear in the definition to that term's HTML page.
   *
   * @param nestedTerms
   *            The value determining whether nested terms mode is on or off
   * @updates this
   */
  void setNestedTermsMode(boolean nestedTerms);

  /**
   * Reports whether {@code this} is in nested terms mode or not.
   *
   * @return The value determining whether nested terms mode is on or off
   */
  boolean isInNestedTermsMode();

  /**
   * Changes the mode of {@code this} to alphabetical order mode if
   * isAlphabetical == true. HTML index pages generated from Glossary will be
   * listed in lexicographic ordering.
   *
   * @param isAlphabetical
   *            value determining whether alphabetical order mode is on or off
   * @updates this
   */
  void setAlphabeticalOrderMode(boolean isAlphabetical);

  /**
   * Reports whether or not {@code this} is in alphabetical order mode.
   *
   * @return The value determining whether alphabetical order mode is on or
   *         off
   */
  boolean isInAlphabeticalOrderMode();

  /**
   * Sets the background image for the HTML index and definition pages.
   *
   * @param imageURL
   *            The location of the background image
   * @updates this
   */
  void setBackgroundImage(String imageURL);

  /**
   * Removes and returns the current background image from the HTML index and
   * definition pages.
   *
   * @return The current background image
   */
  String removeBackgroundImage();
}

