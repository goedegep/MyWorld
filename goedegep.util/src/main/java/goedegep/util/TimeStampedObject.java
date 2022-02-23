package goedegep.util;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.logging.Logger;

/**
 * This class provides a time-stamped object.
 * <p>
 * It contains an object of type T and a <code>LocalDataTime</code> time-stamp.<br/>
 * The time-stamp can be used for sorting, for which a <code>Comparator</code> is available via the method {@link #getTimeStampComparator}.
 *
 * @param <T> The object type.
 */
public class TimeStampedObject<T> {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(TimeStampedObject.class.getName());

  private T object;
  private LocalDateTime timeStamp;
  
  /**
   * Constructor.
   * 
   * @param object the object.
   * @param timeStamp the time-stamp, which may not be null.
   */
  public TimeStampedObject(T object, LocalDateTime timeStamp) {
    if (timeStamp == null) {
      throw new IllegalArgumentException("timeStamp is null for object \"" + (object != null ? object.toString() : "null") + "\"");
    }
    this.object = object;
    this.timeStamp = timeStamp;
  }

  /**
   * Get the object.
   * 
   * @return the object.
   */
  public T getObject() {
    return object;
  }

  /**
   * Get the time-stamp.
   * 
   * @return the time-stamp.
   */
  public LocalDateTime getTimeStamp() {
    return timeStamp;
  }

  /**
   * Get a comparator for sorting on the time-stamp.
   * 
   * @param <U> The type for the comparator.
   * @return a comparator for sorting on the time-stamp.
   */
  public static <U> Comparator<TimeStampedObject<U>> getTimeStampComparator() {
    return new Comparator<TimeStampedObject<U>>() {

      @Override
      public int compare(TimeStampedObject<U> o1, TimeStampedObject<U> o2) {
        return o1.getTimeStamp().compareTo(o2.getTimeStamp());
      }
      
    };
  }
}
