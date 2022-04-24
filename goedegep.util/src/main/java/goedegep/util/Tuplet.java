package goedegep.util;

/**
 * This class stores two values (objects), of types T and U respectively.
 * <p>
 * It can e.g. be used to store an Id and a corresponding string.<br/>
 * The main use however will be for methods that actually return 2 values, which isn't possible in Java.
 * So the values are stored in this tuplet.
 *
 * @param <T> the type of the first object.
 * @param <U> the type of the second object.
 */
public class Tuplet<T, U> {
  private static final String NEWLINE = System.getProperty("line.separator");

  private T    object1;
  private U    object2;

  /**
   * Constructor.
   * 
   * @param object1 the first object.
   * @param object2 the second object.
   */
  public Tuplet(T object1, U object2) {
    this.object1 = object1;
    this.object2 = object2;
  }

  /**
   * Get the first object.
   * 
   * @return the first object.
   */
  public T getObject1() {
    return object1;
  }

  /**
   * Get the second object.
   * 
   * @return the second object.
   */
  public U getObject2() {
    return object2;
  }
  
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("{").append(NEWLINE).append(" ");
    buf.append(object1 != null ? object1.toString() : "(null)");
    buf.append(",").append(NEWLINE).append(" ");
    buf.append(object2 != null ? object2.toString() : "(null)");
    buf.append(NEWLINE).append("}");
    
    return buf.toString();
  }
}