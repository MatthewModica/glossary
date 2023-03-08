package com.modica.glossary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import components.map.Map;
import components.map.Map2;
import components.queue.Queue;
import components.queue.Queue2;
import components.set.Set;
import components.set.Set2;

/**
 * JUnit test class for GlossaryUtilities.
 *
 * @author Matthew Modica
 *
 */
public class GlossaryUtilitiesTest {

  /**
   * Generate a Set of characters representing separators.
   *
   * @return Set of separators
   */
  private static Set<Character> generateSet() {
    Set<Character> separators = new Set2<>();
    final Character[] separatorList = { '\t', '\n', '\r', '.', ',', '?',
        '!', ' ', ':', ';', '"', '-', '[', ']', '(', ')', '/', '\'' };
    for (Character c : separatorList) {
      separators.add(c);
    }
    return separators;
  }

  /**
   * Generate a test Map<String, String>.
   *
   * @return the generated Map
   */
  private static Map<String, String> generateMap() {
    Map<String, String> map = new Map2<>();
    map.add("Ohio", "State");
    map.add("Cleveland", "Browns");

    return map;
  }

  /**
   * A Set of separators.
   */
  static final Set<Character> SEPARATORS = generateSet();

  public void testNextWordOrSeparatorWord0() {
    String s = "Ohio State Buckeyes";
    int pos = 0;
    String result = GlossaryUtilities.nextWordOrSeparator(s, pos,
        SEPARATORS);
    String resultExpected = "Ohio";

    assertEquals(resultExpected, result);
  }

  public void testNextWordOrSeparatorSep0() {
    String s = ", Ohio State Buckeyes";
    int pos = 0;
    String result = GlossaryUtilities.nextWordOrSeparator(s, pos,
        SEPARATORS);
    String resultExpected = ",";

    assertEquals(resultExpected, result);
  }

  public void testNextWordOrSeparatorWordMiddle() {
    String s = "Ohio State Buckeyes";
    int pos = 5;
    String result = GlossaryUtilities.nextWordOrSeparator(s, pos,
        SEPARATORS);
    String resultExpected = "State";

    assertEquals(resultExpected, result);
  }

  public void testNextWordOrSeparatorSepMiddle() {
    String s = "Ohio State Buckeyes";
    int pos = 4;
    String result = GlossaryUtilities.nextWordOrSeparator(s, pos,
        SEPARATORS);
    String resultExpected = " ";

    assertEquals(resultExpected, result);
  }

  public void testNextWordOrSeparatorWordEnd() {
    String s = "Ohio State Buckeyes";
    int pos = 11;
    String result = GlossaryUtilities.nextWordOrSeparator(s, pos,
        SEPARATORS);
    String resultExpected = "Buckeyes";

    assertEquals(resultExpected, result);
  }

  public void testNextWordOrSeparatorSepEnd() {
    String s = "Ohio State Buckeyes.";
    int pos = 19;
    String result = GlossaryUtilities.nextWordOrSeparator(s, pos,
        SEPARATORS);
    String resultExpected = ".";

    assertEquals(resultExpected, result);
  }

  public void testGenerateTermQueueEmpty() {
    Map<String, String> map = new Map2<>();
    Map<String, String> mapExpected = new Map2<>();
    boolean sort = true;
    Queue<String> result = GlossaryUtilities.generateTermQueue(map, sort);
    Queue<String> resultExpected = new Queue2<>();

    assertEquals(mapExpected, map);
    assertEquals(resultExpected, result);
  }

  public void testGenerateTermQueueSorted() {
    Map<String, String> map = generateMap();
    Map<String, String> mapExpected = generateMap();
    boolean sort = true;
    Queue<String> result = GlossaryUtilities.generateTermQueue(map, sort);
    Queue<String> resultExpected = new Queue2<>();
    resultExpected.enqueue("Cleveland");
    resultExpected.enqueue("Ohio");

    assertEquals(mapExpected, map);
    assertEquals(resultExpected, result);
  }

  public void testGenerateTermQueueUnsorted() {
    Map<String, String> map = generateMap();
    Map<String, String> mapExpected = generateMap();
    boolean sort = false;
    Queue<String> result = GlossaryUtilities.generateTermQueue(map, sort);
    Queue<String> resultExpected = new Queue2<>();
    resultExpected.enqueue("Ohio");
    resultExpected.enqueue("Cleveland");

    assertEquals(mapExpected, map);
    assertEquals(resultExpected, result);
  }

}
