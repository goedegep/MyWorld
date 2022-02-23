package goedegep.app.finan.postbankapp;

import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.finan.postbank.pbsprek.PbSpRek;

@SuppressWarnings("serial")
public class PbSpRekKwartaaloverzichtenWindow extends AppFrame {
  public PbSpRekKwartaaloverzichtenWindow(Customization customization, PbSpRek account) {
    super("Postbank " + account.getName() + " kwartaaloverzichten", customization, null);
    
    setSize(600, 600);
    
    setContentPane(new PbSpRekKwartaalOverzichtenTable(this, account));
  }
}
