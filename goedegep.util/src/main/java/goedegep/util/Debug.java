package goedegep.util;

/**
 * This class defines a single constant {@code Debug.ON} which can be used to optimize production code.
 * <p>
 * Typical use: <pre>
 *   if (Debug.ON) {
 *     LOGGER.info("Some logging text");
 *   }
 * </pre>
 * 
 * Explanation: if the compiler detects that the condition in the if statement always evaluates to false, the code in the body is left out by the compiler.
 * This means smaller .class files and faster execution. This should particularly be used if the text to be logged is created by calling e.g. toString() on a larger object.<br/>
 * Because the text is created before calling LOGGER.info(), and then LOGGER.info() may decide that nothing is to be done because of the current logging level.
 *
 */
public final class Debug {
  /**
   * Use this constant to switch debug code on or off.
   */
  public static final boolean ON = true;
}
