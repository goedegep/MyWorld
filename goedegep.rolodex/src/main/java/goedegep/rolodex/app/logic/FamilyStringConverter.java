package goedegep.rolodex.app.logic;


import goedegep.rolodex.model.Family;
import goedegep.rolodex.model.Rolodex;
import javafx.util.StringConverter;

/**
 * This class is a {@link StringConverter} for a Country.
 * <p>
 * The toString method returns the country name.<br/>
 * The fromString method retrieves the Country from the provided Rolodex, based on the provided country name.
 */
public class FamilyStringConverter extends StringConverter<Object> {
  private Rolodex rolodex;

  public FamilyStringConverter(Rolodex rolodex) {
    this.rolodex = rolodex;
  }
  
  @Override
  public String toString(Object object) {
    Family family = (Family) object;
    if (family != null) {
      return family.getFamilyName();
    } else {
      return null;
    }
  }

  @Override
  public Object fromString(String familyName) {
    return rolodex.getFamilyList().getFamily(familyName);
  }

}
