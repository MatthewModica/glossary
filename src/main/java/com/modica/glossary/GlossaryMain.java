package com.modica.glossary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
    BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
    final Glossary myGlossary = new Glossary1();

    /*
     * Set parameters for myGlossary
     */
    myGlossary.setTitle("My Glossary");
    myGlossary.setTermColor("#FF0000");
    myGlossary.setBackgroundImage("OhioState.png");
    myGlossary.setNestedTermsMode(true);
    myGlossary.setSortAlphabetically(true);

    System.out.println("Please enter the location of your glossary text file: ");

    try {
      myGlossary.readText(terminalReader.readLine());
    } catch (IOException e) {
      System.out.println("There was an error reading the location of the text file.");
      System.exit(0);
    }


    myGlossary.addEntry("english",
        "the language of England, widely used throughout the world");

    System.out.println("Please enter the name of your output folder: ");

    try {
      myGlossary.outputHTML(terminalReader.readLine());
    } catch (IOException e) {
      System.out.println("There was an error while printing the HTML file.");
      e.printStackTrace();
      System.exit(0);
    }


    myGlossary.size();
    try {
      terminalReader.close();
    } catch (IOException e) {
      System.out.println("There was an error while closing the terminalReader.");
      e.printStackTrace();
    }
  }
}
