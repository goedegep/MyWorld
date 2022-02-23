package goedegep.app.finan.overzichten;

import goedegep.appgen.swing.Customization;
import goedegep.appgen.swing.AppFrame;
import goedegep.finan.basic.SumAccount;

import java.awt.AWTEvent;

@SuppressWarnings("serial")
public class VermogensontwikkelingWindow extends AppFrame {
  private static final String            WINDOW_TITLE = "Vermogensontwikkeling";
  
  SumAccount sumAccount;
  
  public VermogensontwikkelingWindow(Customization customization, SumAccount sumAccount) {
    super(WINDOW_TITLE, customization);
    
    this.sumAccount = sumAccount;

    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      init();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  private void init() {
    this.setSize(900, 600);
    
    // Vermogensontwikkelings grafiek.
    VermogensontwikkelingsGrafiekPanel vermogensontwikkelingsGrafiekPanel = new VermogensontwikkelingsGrafiekPanel(this, "Vermogensontwikkelings", sumAccount);
    getContentPane().add(vermogensontwikkelingsGrafiekPanel);
  }
}
