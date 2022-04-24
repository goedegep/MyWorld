package goedegep.util;

/**
 * This class stores three values (objects), of types T, U and V respectively.
 * <p>
 * It can e.g. be used to store an Id and a corresponding string.<br/>
 * The main use however will be for methods that actually return 3 values, which isn't possible in Java.
 * So the values are stored in this triplet.
 *
 * @param <T> the type of the first object.
 * @param <U> the type of the second object.
 * @param <V> the type of the third object.
 */
public class Triplet<T, U, V> {
  private static final String NEWLINE = System.getProperty("line.separator");

  private T    object1;
  private U    object2;
  private V    object3;

  /**
   * Constructor.
   * 
   * @param object1 the first object.
   * @param object2 the second object.
   * @param object3 the third object.
   */
  public Triplet(T object1, U object2, V object3) {
    this.object1 = object1;
    this.object2 = object2;
    this.object3 = object3;
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

  /**
   * Get the third object.
   * 
   * @return the second object.
   */
  public V getObject3() {
    return object3;
  }
  
  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    
    buf.append("{").append(NEWLINE).append(" ");
    buf.append(object1 != null ? object1.toString() : "(null)");
    buf.append(",").append(NEWLINE).append(" ");
    buf.append(object2 != null ? object2.toString() : "(null)");
    buf.append(",").append(NEWLINE).append(" ");
    buf.append(object3 != null ? object3.toString() : "(null)");
    buf.append(NEWLINE).append("}");
    
    return buf.toString();
  }
}