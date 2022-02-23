package goedegep.rolodex.app.logic;


import java.util.List;

import goedegep.rolodex.model.PhoneNumber;
import javafx.util.StringConverter;

/**
 * This class is a {@link StringConverter} for a list of phone numbers.
 * <p>
 * The toString method returns the list of phone numbers.<br/>
 * The fromString method simply returns null.
 */
public class PhoneNumberListStringConverter extends StringConverter<Object> {
  
  @Override
  public String toString(Object object) {
    @SuppressWarnings("unchecked")
    List<PhoneNumber> phoneNumbers = (List<PhoneNumber>) object;
    if (phoneNumbers != null) {
      StringBuilder buf = new StringBuilder();
      boolean first = true;
      for (PhoneNumber phoneNumber: phoneNumbers) {
        if (first) {
          first = false;
        } else {
          buf.append(", ");
        }
        buf.append(phoneNumber.getPhoneNumber());
        if (phoneNumber.getDescription() != null) {
          buf.append(" (").append(phoneNumber.getDescription()).append(")");
        }
      }
      return buf.toString();
    } else {
      return null;
    }
  }

  @Override
  public Object fromString(String countryName) {
    return null;
  }

}
