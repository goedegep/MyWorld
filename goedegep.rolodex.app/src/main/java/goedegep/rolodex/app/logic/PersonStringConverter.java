package goedegep.rolodex.app.logic;


import goedegep.rolodex.model.Person;
import javafx.util.StringConverter;

/**
 * This class is a {@link StringConverter} for a Person.
 * <p>
 * The toString method returns the persons name.<br/>
 * The fromString method retrieves the Person from the provided Rolodex, based on the provided persons name.
 */
public class PersonStringConverter extends StringConverter<Object> {
  
  @Override
  public String toString(Object object) {
    Person person = (Person) object;
    if (person != null) {
      return person.getName();
    } else {
      return null;
    }
  }

  @Override
  public Object fromString(String personName) {
    return null;
  }

}
