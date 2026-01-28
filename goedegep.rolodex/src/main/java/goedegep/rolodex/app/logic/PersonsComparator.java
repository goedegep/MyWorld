package goedegep.rolodex.app.logic;

import java.util.Comparator;

import goedegep.rolodex.model.Person;

/**
 * This class provides a (singleton) <code>Comparator</code> to compare Persons.
 */
public class PersonsComparator implements Comparator<Person> {
  private static PersonsComparator instance;

  /**
   * Private constructor.
   */
  private PersonsComparator() {
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
    
    String firstName1 = person1.getFirstname();
    String firstName2 = person2.getFirstname();
    if ((firstName1 == null)  &&  (firstName2 != null)) {
      result = -1;
    } else if ((firstName1 != null)  &&  (firstName2 == null)) {
      result = 1;
    } else if ((firstName1 != null)  &&  (firstName2 != null)){
      result = firstName1.compareTo(firstName2);
    }
    
    if (result != 0) {
      return result;
    }
    
    String infix1 = person1.getInfix();
    String infix2 = person2.getInfix();
    if ((infix1 == null)  &&  (infix2 != null)) {
      result = -1;
    } else if ((infix1 != null)  &&  (infix2 == null)) {
      result = 1;
    } else if ((infix1 != null)  &&  (infix2 != null)) {
      result = infix1.compareTo(infix2);
    }
    
    if (result != 0) {
      return result;
    }
    
    String surname1 = person1.getSurname();
    String surname2 = person2.getSurname();
    if ((surname1 == null)  &&  (surname2 != null)) {
      result = -1;
    } else if ((surname1 != null)  &&  (surname2 == null)) {
      result = 1;
    } else if ((surname1 != null)  &&  (surname2 != null)) {
      result = surname1.compareTo(surname2);
    }
    
    return result;
   }

  /**
   * Get the Comparator instance.
   * 
   * @return the Comparator instance.
   */
  public static PersonsComparator getInstance() {
    if (instance == null) {
      instance = new PersonsComparator();
    }
    
    return instance;
  }
}
