package goedegep.rolodex.app.guifx;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.objectcontrols.ObjectControlAutoCompleteTextField;
import goedegep.rolodex.model.Address;
import goedegep.rolodex.model.Rolodex;

/**
 * This class is a TextField, with autocompletion, for an address
 */
public class AddressTextField extends ObjectControlAutoCompleteTextField<String> {
  private static final Logger         LOGGER = Logger.getLogger(AddressTextField.class.getName());
  private Rolodex rolodex;
  
  /**
   * Constructor.
   */
  public AddressTextField(CustomizationFx customization, Rolodex rolodex) {
    super(null, 300, true, "Select an address");
    
    this.rolodex = rolodex;
    
    customization.getComponentFactoryFx().customizeTextInputControl(this);
    getEntries().addAll(addressesToString(rolodex.getAddressList().getAddresses()));
   }
  
//  /**
//   * {@inheritDoc}
//   */
//  @Override
//  public boolean isDataValid() {    
//    return (getMatchingAddress() != null);
//  }
  
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
      LOGGER.severe("adding address: " + address);
      addressesTexts.add(address.toString());
    }
    
    return addressesTexts;
  }
}