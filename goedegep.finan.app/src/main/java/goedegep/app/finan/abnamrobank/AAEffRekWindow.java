package goedegep.app.finan.abnamrobank;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import goedegep.app.finan.effrek.EffRekWindow;
import goedegep.app.finan.gen.FinanBank;
import goedegep.appgen.swing.Customization;
import goedegep.finan.abnamrobank.aaeffrek.AAEffRek;

@SuppressWarnings("serial")
public class AAEffRekWindow extends EffRekWindow {
  private static final String     WINDOW_TITLE = "ABN AMRO Effectenrekening";
  
  //Construct the frame
  public AAEffRekWindow(Customization customization, FinanBank finanBank, AAEffRek effectenRekening) {
    this(customization, finanBank, effectenRekening, LocalDate.now());
  }
  
  public AAEffRekWindow(Customization customization, FinanBank finanBank, AAEffRek effectenRekening, LocalDate statusDate) {
    super(WINDOW_TITLE, customization, new Dimension(800, 600), finanBank, effectenRekening);

//    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
//    try {
//      init();
//    }
//    catch(Exception e) {
//      e.printStackTrace();
//    }
  }

  // Transacties | Test Window
  protected void testWindow_actionPerformed(ActionEvent e) {
    boolean             packFrame = false;
    AAEffRekTestWindow  window = new AAEffRekTestWindow(getCustomization(), getFinanBank(), (AAEffRek) getEffRek());

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame)
      window.pack();
    else
      window.validate();
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = window.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    window.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2 + 22);
    window.setVisible(true);
  }
}