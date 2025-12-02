package goedegep.travels.app.guifx;

import java.util.ListResourceBundle;

/**
 * This class provides the English/default texts for the Vacations Property Descriptors.
 */
public class TravelsPropertyDescriptorsResource extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    return new Object[][] {
      
      /*
       *  VacationsWindow
       */
      {"Vacations.vacationsFileName.description", "Name of the file with information about all vacations."},
      
      {"Vacations.vacationChecklistFileName.description", "Name of the file with the vacation checklist information."},
      
      {"Vacations.vacationsFolderName.description", "Name of the folder with information about vacations."},
      
      {"Vacations.vacationPicturesFolderName.description", "Name of the folder with vacation pictures."},
      
      {"Vacations.askToEditProperties.description", "If true, the user will be asked to edit the User Properties at application startup."},
      
      {"Vacations.knownFiles.description", "Known files about which no warnings are shown"},
      
      {"Vacations.ignoreVacationPictureFolders.description", "Folders that will be ignored"}

    };
  }

}
