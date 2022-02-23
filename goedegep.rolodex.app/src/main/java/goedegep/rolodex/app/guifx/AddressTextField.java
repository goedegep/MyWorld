package goedegep.rolodex.app.guifx;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.textfield.TextFields;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.controls.TextFieldObjectInput;
import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.Rolodex;

/**
 * This class is a TextField, with autocompletion, for an address
 */
public class AddressTextField extends TextFieldObjectInput<String> {
  private Rolodex rolodex;
  
  /**
   * Constructor.
   */
  public AddressTextField(CustomizationFx customization, Rolodex rolodex) {
    super(null, 300, true, "Select an address");
    
    this.rolodex = rolodex;
    
    customization.getComponentFactoryFx().customizeTextInputControl(this);
    TextFields.bindAutoCompletion(this, addressesToString(rolodex.getAddressList().getAddresses()));
  }
  
  @Override
  public boolean getIsValid(StringBuilder buf) {
    
    if (isOptional()  &&  !getIsFilledIn()) {
      return true;
    }
        
    return (getMatchingAddress() != null);
  }
  
  public Address getMatchingAddress() {
    String addressText = getText();
    
    for (Address address: rolodex.getAddressList().getAddresses()) {
      if (address.toString().equals(addressText)) {
        return address;
      }
    }
    
    return null;
  }
    
  private static List<String> addressesToString(List<Address> addresses) {
    List<String> addressesTexts = new ArrayList<>();
    
    for (Address address: addresses) {
      addressesTexts.add(address.toString());
    }
    
    return addressesTexts;
  }
}