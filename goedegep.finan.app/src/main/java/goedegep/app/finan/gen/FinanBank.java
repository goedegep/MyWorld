package goedegep.app.finan.gen;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import goedegep.appgen.swing.Customization;
import goedegep.finan.basic.Bank;

/**
 * This class adds 'Finan'presentation information to a 'Bank'.
 * <p>
 * The class contains:
 * <ul>
 * <li>
 * the {@link Bank} to which the information applies.
 * </li>
 * <li>
 * an {@link Icon} with the banks logo, in enabled state.
 * </li>
 * <li>
 * an {@link Icon} with the banks logo, in disable state.
 * </li>
 * </ul>
 * Note: These icons cannot be supplied as part of a {@link Customization}, because a single window may provide information from several banks.
 */
public class FinanBank {
  Bank bank;
  Icon bankLogoIcon;
  Icon bankLogoIconDisabled;
  
  
  /**
   * Create a FinanBank object, with all properties specified.
   * 
   * @param bank the Bank to which the information applies
   * @param bankLogoIcon the Banks logo icon for the enabled state
   * @param bankLogoIconDisabled the banks logo icon for the disabled state
   */
  public FinanBank(Bank bank, Icon bankLogoIcon, Icon bankLogoIconDisabled) {
    super();
    this.bank = bank;
    this.bankLogoIcon = bankLogoIcon;
    this.bankLogoIconDisabled = bankLogoIconDisabled;
  }

  /**
   * Get the Bank to which the information applies.
   * 
   * @return the Bank to which the information applies.
   */
  public Bank getBank() {
    return bank;
  }

  /**
   * Set the Bank to which the information applies.
   * 
   * @param bank the Bank to which the information applies
   */
  public void setBank(Bank bank) {
    this.bank = bank;
  }

  /**
   * Get the Banks logo icon for the enabled state.
   * 
   * @return the Banks logo icon for the enabled state
   */
  public ImageIcon getBankLogoIcon() {
    return (ImageIcon) bankLogoIcon;
  }

  /**
   * Set the Banks logo icon for the enabled state.
   * 
   * @param bankLogoIcon the Banks logo icon for the enabled state
   */
  public void setBankLogoIcon(Icon bankLogoIcon) {
    this.bankLogoIcon = bankLogoIcon;
  }

  /**
   * Get the Banks logo icon for the disabled state.
   * 
   * @return the Banks logo icon for the disabled state
   */
  public Icon getBankLogoIconDisabled() {
    return bankLogoIconDisabled;
  }

  /**
   * Set the Banks logo icon for the disabled state.
   * 
   * @param bankLogoIconDisabled the Banks logo icon for the disabled state
   */
  public void setBankLogoIconDisabled(Icon bankLogoIconDisabled) {
    this.bankLogoIconDisabled = bankLogoIconDisabled;
  }
  
  @Override
  public String toString() {
    return bank.getName();
  }
}
