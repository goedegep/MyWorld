package goedegep.app.finan.direktbankapp;

import java.awt.Dimension;

import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.finan.direktbank.direktsprek.DirektSpRek;


@SuppressWarnings("serial")
public class DirektSpRekKwartaaloverzichtenWindow extends AppFrame {
  public DirektSpRekKwartaaloverzichtenWindow(Customization customization, DirektSpRek account) {
    super("Direktbank " + account.getName() + " kwartaaloverzichten", customization, new Dimension(600, 600));
    
    setSize(600, 600);
    
    setContentPane(new DirektSpRekKwartaalOverzichtenTable(this, account));
  }
  
  
}