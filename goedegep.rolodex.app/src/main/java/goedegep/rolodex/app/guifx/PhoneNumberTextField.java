package goedegep.rolodex.app.guifx;

import java.util.ArrayList;
import java.util.List;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.controls.AutoCompleteTextFieldObjectInput;
import goedegep.rolodex.model.PhoneNumber;
import goedegep.rolodex.model.Rolodex;

/**
 * This class is a TextField, with autocompletion, for a phone number
 */
public class PhoneNumberTextField extends AutoCompleteTextFieldObjectInput<String> {
  private Rolodex rolodex;
  
  /**
   * Constructor.
   */
  public PhoneNumberTextField(CustomizationFx customization, Rolodex rolodex) {
    super(null, 100, true, "Select a phone number");
    
    this.rolodex = rolodex;
    
    customization.getComponentFactoryFx().customizeTextInputControl(this);
    getEntries().addAll(phoneNumberToString(rolodex.getPhoneNumberList().getPhoneNumbers()));
  }
  
  @Override
  public boolean getIsValid(StringBuilder buf) {
    
    if (isOptional()  &&  !getIsFilledIn()) {
      return true;
    }
        
    return (getMatchingPhoneNumber() != null);
  }
  
  public PhoneNumber getMatchingPhoneNumber() {
    String phoneNumberText = getText();
    
    for (PhoneNumber phoneNumber: rolodex.getPhoneNumberList().getPhoneNumbers()) {
      if (phoneNumber.toString().equals(phoneNumberText)) {
        return phoneNumber;
      }
    }
    
    return null;
  }
    
  private static List<String> phoneNumberToString(List<PhoneNumber> phoneNumbers) {
    List<String> phoneNumbersTexts = new ArrayList<>();
    
    for (PhoneNumber phoneNumber: phoneNumbers) {
      phoneNumbersTexts.add(phoneNumber.toString());
    }
    
    return phoneNumbersTexts;
  }
}