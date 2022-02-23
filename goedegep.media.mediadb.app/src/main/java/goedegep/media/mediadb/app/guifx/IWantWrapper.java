package goedegep.media.mediadb.app.guifx;


import java.util.ArrayList;
import java.util.List;

import goedegep.media.mediadb.model.IWant;

public class IWantWrapper {
  private static List<IWantWrapper> values;

  private IWant iWant;
  
  static {
    values = new ArrayList<>();
    for (IWant iWant: IWant.values()) {
      values.add(new IWantWrapper(iWant));
    }
  }
  
  public IWantWrapper(IWant iWant) {
    this.iWant = iWant;
  }
  
  public String toString() {
    return iWant.getLiteral();
  }
  
  public static List<IWantWrapper> values() {
    return values;
  }
  
  public static IWantWrapper forIWant(IWant iWant) {
    for (IWantWrapper iWantWrapper: values) {
      if (iWantWrapper.iWant.equals(iWant)) {
        return iWantWrapper;
      }
    }
    
    return null;
  }
  
  public IWant getIWant() {
    return iWant;
  }
}
