package goedegep.rolodex.app.guifx;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.textfield.TextFields;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.controls.TextFieldObjectInput;
import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.Rolodex;

/**
 * This class is a TextField, with autocompletion, for an Institution
 */
public class InstitutionTextField extends TextFieldObjectInput<String> {
  private Rolodex rolodex;
  
  /**
   * Constructor.
   */
  public InstitutionTextField(CustomizationFx customization, Rolodex rolodex) {
    super(null, 300, false, "Enter the name of an institution");
    
    this.rolodex = rolodex;
    
    customization.getComponentFactoryFx().customizeTextInputControl(this);
    TextFields.bindAutoCompletion(this, institutionsToString(rolodex.getInstitutionList().getInstitutions()));
  }
  
  @Override
  public boolean getIsValid(StringBuilder buf) {
    
    if (isOptional()  &&  !getIsFilledIn()) {
      return true;
    }
        
    return (!getMatchingInstitutions().isEmpty());
  }
  
  public boolean isNonExistingInstitutionName() {
    if (getIsFilledIn() && (getMatchingInstitutions().isEmpty())) {
      return true;
    } else {
      return false;
    }
  }
  
  public List<Institution> getMatchingInstitutions() {
    List<Institution> matchingInstitutions = new ArrayList<>();
    
    for (Institution institution: rolodex.getInstitutionList().getInstitutions()) {
      if (institution.getName().equals(getText())) {
        matchingInstitutions.add(institution);
      }
    }
    
    return matchingInstitutions;
  }
  
//  public City getCity(Country country) {
//    return rolodex.getCityList().getCity(getText(), country);
//  }
  
  private static List<String> institutionsToString(List<Institution> institutions) {
    List<String> institutionNames = new ArrayList<>();
    
    for (Institution institution: institutions) {
      institutionNames.add(institution.getName());
    }
    
    return institutionNames;
  }
}