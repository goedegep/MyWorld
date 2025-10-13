package goedegep.vacations.app.guifx;

import java.util.ListResourceBundle;

/**
 * This class provides the English/default texts for the Vacations application.
 */
public class VacationsResource extends ListResourceBundle {
  private static final String NEWLINE = System.getProperty("line.separator");

  @Override
  protected Object[][] getContents() {
    return new Object[][] {
      
      /*
       *  VacationsWindow
       */
      {"VacationsWindow.windowTitle", "Travels"},
      
      {"VacationsWindow.noVacationSelectedMsg", "No vacation selected"},
      
      {"VacationsWindow.statusLabel.noVacationsFileNameMsg", "No vacations filename configured, configure the filename and restart the application"},
      {"VacationsWindow.alertNoVacationsFileName.header", "There's no filename configured for the file with vacations"},
      {"VacationsWindow.alertNoVacationsFileName.content", "Configure the filename (e.g. via the 'Edit User Settings' below) and restart the application." +
        NEWLINE +
        "A restart is needed, because the settings are only read at startup."},
      
      {"VacationsWindow.statusLabel.noVacationChecklistFileNameMsg", "No vacation checklist filename configured, configure the filename and restart the application"},
      {"VacationsWindow.alertNoVacationChecklistFileName.header", "There's no filename configured for the file with vacation checklist information"},
      {"VacationsWindow.alertNoVacationChecklistFileName.content", "Configure the filename (e.g. via the 'Edit User Settings' below) and restart the application." +
        NEWLINE +
        "A restart is needed, because the settings are only read at startup."},
      
      {"VacationsWindow.alertNoVacationsFileName.editorButton", "Edit User Settings"},
      
      {"VacationsWindow.alertVacationsFileNotFound.header", "The file with vacations ({0}) doesn't exist yet."},
      {"VacationsWindow.alertVacationsFileNotFound.content", "Do you want to create this file now?" + NEWLINE +
        "If you choose \"No\" you can't do anything in this screen."},
      
      {"VacationsWindow.alertVacationChecklistFileNotFound.header", "The file with the vacation checklist information ({0}) doesn't exist yet."},
      {"VacationsWindow.alertVacationChecklistFileNotFound.content", "Do you want to create this file now?" + NEWLINE +
        "If you choose \"No\" you can't do anything with checklists."}
    };
  }

}
