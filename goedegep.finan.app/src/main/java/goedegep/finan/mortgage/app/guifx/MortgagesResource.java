package goedegep.finan.mortgage.app.guifx;

import java.util.ListResourceBundle;

public class MortgagesResource extends ListResourceBundle {
  private static final String NEWLINE = System.getProperty("line.separator");

  @Override
  protected Object[][] getContents() {
    return new Object[][] {
      /*
       * General Controls
       */
      {"Button.yes", "Yes"},
      {"Button.no", "No"},
      
      /*
       *  MortgagesWindow
       */
      {"MortgagesWindow.windowTitle", "Mortgages"},
      {"MortgagesWindow.statusLabel.noMortgagesFileNameMsg", "No mortgages filename configured, configure the filename and restart the application"},
      {"MortgagesWindow.alertNoMortgagesFileName.header", "No mortgages filename configured"},
      {"MortgagesWindow.alertNoMortgagesFileName.content",
        "There's no filename configured for the file with mortgages" +
        NEWLINE +
        "Configure the filename (e.g. via the 'Edit User Settings' below) and restart the application." +
        NEWLINE +
        "A restart is needed, because the settings are only read at startup."},
      {"MortgagesWindow.alertNoMortgagesFileName.editorButton", "Edit User Settings"},
      {"MortgagesWindow.alertMortgagesFileNotFound.title", "Mortgages file not found"},
      {"MortgagesWindow.alertMortgagesFileNotFound.header", "The file with mortgages ({0}) doesn't exist yet."},
      {"MortgagesWindow.alertMortgagesFileNotFound.content", "Do you want to create this file now?" + NEWLINE +
        "If you choose \"No\" you can't do anything in this screen."}
    };
  }


}
