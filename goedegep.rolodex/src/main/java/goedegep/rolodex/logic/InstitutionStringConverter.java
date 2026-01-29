package goedegep.rolodex.logic;


import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.Rolodex;
import javafx.util.StringConverter;

/**
 * This class is a {@link StringConverter} for a Institution.
 * <p>
 * The toString method returns the institution name.<br/>
 * The fromString method retrieves the Institution from the provided Rolodex, based on the provided institution name.
 */
public class InstitutionStringConverter extends StringConverter<Object> {
  private Rolodex rolodex;

  public InstitutionStringConverter(Rolodex rolodex) {
    this.rolodex = rolodex;
  }
  
  @Override
  public String toString(Object object) {
    Institution institution = (Institution) object;
    if (institution != null) {
      return institution.getName();
    } else {
      return null;
    }
  }

  @Override
  public Object fromString(String institutionName) {
    return rolodex.getInstitutionList().getInstitution(institutionName);
  }

}
