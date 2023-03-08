package com.modica.glossary;

import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Class for creating a Glossary and outputting the HTML representation of the
 * Glossary.
 *
 * @author Matthew Modica
 */
public final class GlossaryMain {

  /**
   * Main method.
   *
   * @param args
   *            the command line arguments; unused here
   */
  public static void main(String[] args) {
    final SimpleWriter out = new SimpleWriter1L();
    final SimpleReader in = new SimpleReader1L();
    final Glossary myGlossary = new Glossary1();

    /*
     * Set parameters for myGlossary
     */
    myGlossary.setTitle("My Glossary");
    myGlossary.setTermColor("#FF0000");
    myGlossary.setBackgroundImage("OhioState.png");
    myGlossary.setNestedTermsMode(true);
    myGlossary.setAlphabeticalOrderMode(true);

    out.println("Please enter the location of your glossary text file: ");

    myGlossary.readText(in.nextLine());

    myGlossary.addEntry("english",
        "the language of England, widely used throughout the world");

    out.println("Please enter the name of your output folder: ");
    myGlossary.outputHTML(in.nextLine());

    myGlossary.size();

    in.close();
    out.close();
  }
}
