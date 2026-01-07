package goedegep.finan.mortgage.guifx;

import java.util.ArrayList;
import java.util.List;

import goedegep.finan.mortgage.model.MortgageType;

public class MortgageTypeWrapper {
  private static List<MortgageTypeWrapper> values = new ArrayList<>();
  
  private MortgageType mortgageType;
  private String displayName;
  
  static {
    values.add(new MortgageTypeWrapper(MortgageType.ANNUITY, "Annuity"));
    values.add(new MortgageTypeWrapper(MortgageType.INTEREST_ONLY, "Interest only"));
  }
  
  private MortgageTypeWrapper(MortgageType mortgageType, String displayName) {
    this.mortgageType = mortgageType;
    this.displayName = displayName;
  }
  
  public static List<MortgageTypeWrapper> values() {
    return values;
  }
  
  public static MortgageTypeWrapper getMortgageTypeWrapper(MortgageType mortgageType) {
    for (MortgageTypeWrapper mortgageTypeWrapper: values) {
      if (mortgageTypeWrapper.mortgageType.equals(mortgageType)) {
        return mortgageTypeWrapper;
      }
    }
    
    return null;
  }
  
  public MortgageType getMortgageType() {
    return mortgageType;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
