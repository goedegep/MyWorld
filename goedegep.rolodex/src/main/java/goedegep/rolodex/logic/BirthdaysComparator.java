package goedegep.rolodex.logic;

import java.util.Comparator;

import goedegep.rolodex.model.Birthday;
import goedegep.rolodex.model.Person;

/**
 * This class provides a (singleton) <code>Comparator</code> to compare Cities.
 */
public class BirthdaysComparator implements Comparator<Person> {
  private static BirthdaysComparator instance;

  /**
   * Private constructor.
   */
  private BirthdaysComparator() {
  }
  
  @Override
  public int compare(Person person1, Person person2) {
    int result = 0;
    
    if ((person1 == null)  &&  (person2 == null)) {
      return 0;
    } else if ((person1 == null)  &&  (person2 != null)) {
      result = -1;
    } else if ((person1 != null)  &&  (person2 == null)) {
      result = 1;
    }
    
    if (result != 0) {
      return result;
    }    
    
    Birthday birthday1 = person1.getBirthday();
    Birthday birthday2 = person2.getBirthday();
    if ((birthday1 == null)  &&  (birthday2 != null)) {
      result = -1;
    } else if ((birthday1 != null)  &&  (birthday2 == null)) {
      result = 1;
    } else if ((birthday1 != null)  &&  (birthday2 != null)) {
      result = compareBirthdays(birthday1, birthday2);
    }
    
    if (result != 0) {
      return result;
    }
        
    return person1.getName().compareTo(person2.getName());
  }
  
  private int compareBirthdays(Birthday birthday1, Birthday birthday2) {
    int result = Integer.compare(birthday1.getMonth(), birthday2.getMonth());
    if (result != 0) {
      return result;
    }
    
    return Integer.compare(birthday1.getDay(), birthday2.getDay());
  }

  /**
   * Get the Comparator instance.
   * 
   * @return the Comparator instance.
   */
  public static BirthdaysComparator getInstance() {
    if (instance == null) {
      instance = new BirthdaysComparator();
    }
    
    return instance;
  }
}
