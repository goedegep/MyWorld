package goedegep.app.finan.direktbankapp;

import goedegep.app.finan.abnamrobank.AbnAmroBankResources;
import goedegep.appgen.swing.AppResources;

public class DirektBank {
  private static AppResources appResources = null;
  
  public static AppResources getAppResources() {
    if (appResources == null) {
      appResources = new AbnAmroBankResources();
    }
    
    return appResources;
  }

}
