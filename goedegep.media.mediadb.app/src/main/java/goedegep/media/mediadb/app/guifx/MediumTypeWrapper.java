package goedegep.media.mediadb.app.guifx;


import java.util.ArrayList;
import java.util.List;

import goedegep.media.mediadb.model.MediumType;

public class MediumTypeWrapper {
  private static List<MediumTypeWrapper> values;

  private MediumType mediumType;
  
  static {
    values = new ArrayList<>();
    for (MediumType mediumType: MediumType.values()) {
      values.add(new MediumTypeWrapper(mediumType));
    }
  }
  
  public MediumTypeWrapper(MediumType mediumType) {
    this.mediumType = mediumType;
  }
  
  public String toString() {
    return mediumType.getLiteral();
  }
  
  public static List<MediumTypeWrapper> values() {
    return values;
  }
}
