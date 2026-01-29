package goedegep.rolodex.gui;

import java.util.ArrayList;
import java.util.List;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.rolodex.model.Institution;
import goedegep.rolodex.model.Rolodex;

/**
 * This class is a TextField, with autocompletion, for an Institution
 */
public class InstitutionTextField extends ObjectControlAutoCompleteTextField<String> {
  private Rolodex rolodex;
  
  /**
   * Constructor.
   */
  public InstitutionTextField(CustomizationFx customization, Rolodex rolodex) {
    super(customization, 300, false, "Enter the name of an institution");
    
    this.rolodex = rolodex;
    
    customization.getComponentFactoryFx().customizeTextInputControl(this.getControl());
    getControl().getEntries().addAll(institutionsToString(rolodex.getInstitutionList().getInstitutions()));
  }
  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public boolean isDataValid() {
//    return (!getMatchingInstitutions().isEmpty());
//  }
  
  public boolean isNonExistingInstitutionName() {
    if (isFilledIn() && getMatchingInstitutions().isEmpty()) {
      return true;
    } else {
      return false;
    }
  }
  
  public List<Institution> getMatchingInstitutions() {
    List<Institution> matchingInstitutions = new ArrayList<>();
    
    for (Institution institution: rolodex.getInstitutionList().getInstitutions()) {
      if (institution.getName().equals(getValue())) {
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