package com.modica.glossary;

/**
 * {@link Glossary} enhanced with secondary methods.
 *
 * @author Matthew Modica
 *
 */

public interface Glossary extends GlossaryKernel {

  /**
   * Loads the properly formatted text from {@code fileName} into
   * {@code this}.
   *
   * @param fileName
   *            The name of the .txt file containing the glossary information
   * @replaces this
   * @requires fileName is a valid file location
   */
  void readText(String fileName);

  /**
   * Outputs a well formatted HTML index page containing each term in
   * {@code this} and an HTML page displaying the definition for the
   * corresponding term.
   *
   * @param folderName
   *            The name of the folder to store the HTML files in
   * @requires folderName is a valid folder location
   */
  void outputHTML(String folderName);

}
