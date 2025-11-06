package goedegep.vacations.app.guifx;

import java.util.ListResourceBundle;

/**
 * This class provides the Dutch texts for the Vacations Property Descriptors.
 */
public class VacationsPropertyDescriptorsResource_nl extends ListResourceBundle {

  @Override
  protected Object[][] getContents() {
    return new Object[][] {
      
      // VacationsWindow
      {"Vacations.vacationsFileName.description", "Naam van het bestand met de informatie van alle vakanties"},
      
      {"Vacations.vacationChecklistFileName.description", "Naam van het bestand met de vakantie checklist informatie."},
      
      {"Vacations.vacationsFolderName.description", "Naam van de map met informatie per vakantie."},
      
      {"Vacations.vacationPicturesFolderName.description", "Naam van de map met vakantie foto's."},
      
      {"Vacations.askToEditProperties.description", "Als deze vlag gezet is wordt de gebruiker gevraagd om de gebruikerseigenschappen te bewerken."},
      
      {"Vacations.knownFiles.description", "Bestanden die bekend zijn en waar geen meldingen over gegeven worden"},
      
      {"Vacations.ignoreVacationPictureFolders.description", "Mappen die genegeerd worden"}
    };
  }

}
