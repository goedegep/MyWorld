package goedegep.app.finan.postbankapp;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.logging.Logger;

import goedegep.app.finan.effrek.EffRekWindow;
import goedegep.app.finan.gen.FinanBank;
import goedegep.appgen.WindowUtil;
import goedegep.appgen.swing.Customization;
import goedegep.finan.postbank.pbeffrek.PbEffRek;

/**
 * This class provides a "Postbank Effectenrekening" Window.
 * <p>
 * This window is an (extends) {@link EffRekwindow}. It only implements method <b>testWindow_actionPerformed</b>.
 */
@SuppressWarnings("serial")
public class PbEffRekWindow extends EffRekWindow {
  private final static Logger     LOGGER = Logger.getLogger(PbEffRekWindow.class.getName()); 
  private final static String     WINDOW_TITLE = "Postbank Effectenrekening";


  /**
   * Create a "Postbank Effectenrekening" Window, where the status is provided for the current date.
   * 
   * @param customization the GUI customization
   * @param finanBank 
   * @param effectenRekening the securities account for which the information is shown in the window
   */
  public PbEffRekWindow(Customization customization, FinanBank finanBank, PbEffRek effectenRekening) {
    this(customization, finanBank, effectenRekening, LocalDate.now());
  }
  
  public PbEffRekWindow(Customization customization, FinanBank finanBank, PbEffRek effectenRekening, LocalDate statusDate) {
    super(WINDOW_TITLE, customization, new Dimension(800, 600), finanBank, effectenRekening, statusDate);
    LOGGER.fine("=>");

    LOGGER.fine("<=");
  }

  // Transacties | Test Window
  protected void testWindow_actionPerformed(ActionEvent e) {
    boolean             packFrame = false;
    PbEffRekTestWindow  window = new PbEffRekTestWindow(getCustomization(), getFinanBank(), getEffRek());

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame)
      window.pack();
    else
      window.validate();
    WindowUtil.showFrameCenteredOnScreen(window, 22, 22);
  }
}