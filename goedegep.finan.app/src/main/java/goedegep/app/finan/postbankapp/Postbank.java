package goedegep.app.finan.postbankapp;

import goedegep.app.finan.abnamrobank.AbnAmroBankResources;
import goedegep.appgen.swing.AppResources;

public class Postbank {
  private static AppResources appResources = null;
  
  public static AppResources getAppResources() {
    if (appResources == null) {
      appResources = new AbnAmroBankResources();
    }
    
    return appResources;
  }

}
