package goedegep.travels.gui;

import java.util.ListResourceBundle;

/**
 * This class provides the Dutch texts for the Vacations application.
 */
public class TravelsResource_nl extends ListResourceBundle {
  private static final String NEWLINE = System.getProperty("line.separator");

  @Override
  protected Object[][] getContents() {
    return new Object[][] {
      
      // VacationsWindow
      {"VacationsWindow.windowTitle", "Reizen"},
      {"VacationsWindow.noVacationSelectedMsg", "Geen vakantie geselecteerd"},
      
      {"VacationsWindow.statusLabel.noVacationsFileNameMsg", "Geen vakanties bestandnaam geconfigureerd, configureer de bestandsnaam en start de applicatie opnieuw op"},
      {"VacationsWindow.alertNoVacationsFileName.title", "Geen vakanties bestandnaam geconfigureerd"},
      {"VacationsWindow.alertNoVacationsFileName.header", "Er is geen bestandnaam geconfigureerd voor het bestand met de informatie over de vakanties."},
      {"VacationsWindow.alertNoVacationsFileName.content", "Configureer de bestandsnaam (bijv. door op onderstaande 'Gebruikers instellingen bewerken' knop the klikken) en start de applicatie opnieuw op." + NEWLINE +
        "Het opnieuw starten is nodig, omdat de instellingen alleen tijdens het opstarten van de applicatie gelezen worden."},
      
      {"VacationsWindow.statusLabel.noVacationChecklistFileNameMsg", "Geen vakantie checklist bestandnaam geconfigureerd, configureer de bestandsnaam en start de applicatie opnieuw op"},
      {"VacationsWindow.alertNoVacationChecklistFileName.title", "Geen vakantie checklist bestandnaam geconfigureerd"},
      {"VacationsWindow.alertNoVacationChecklistFileName.header", "Er is geen bestandnaam geconfigureerd voor het bestand met de vakantie checklist informatie."},
      {"VacationsWindow.alertNoVacationChecklistFileName.content", "Configureer de bestandsnaam (bijv. door op onderstaande 'Gebruikers instellingen bewerken' knop the klikken) en start de applicatie opnieuw op." + NEWLINE +
      "Het opnieuw starten is nodig, omdat de instellingen alleen tijdens het opstarten van de applicatie gelezen worden."},
        
      {"VacationsWindow.alertNoVacationsFileName.editorButton", "Gebruikers instellingen bewerken"},
      
      {"VacationsWindow.alertVacationsFileNotFound.header", "Het bestand met vakanties ({0}) bestaat nog niet."},
      {"VacationsWindow.alertVacationsFileNotFound.content", "Wil je dit bestand nu aanmaken?" + NEWLINE +
        "Als je \"Nee\" kiest kan je in dit scherm niets doen."},
      
      {"VacationsWindow.alertVacationChecklistFileNotFound.header", "Het bestand met vakanties checklist informatie ({0}) bestaat nog niet."},
      {"VacationsWindow.alertVacationChecklistFileNotFound.content", "Wil je dit bestand nu aanmaken?" + NEWLINE +
        "Als je \"Nee\" kiest kan je niets met checklists doen."}
    };
  }

}
