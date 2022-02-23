package goedegep.finan.mortgage.app.guifx;

import java.util.ListResourceBundle;

public class MortgagesResource_nl extends ListResourceBundle {
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
      {"MortgagesWindow.statusLabel.noMortgagesFileNameMsg", "Geen hypotheken bestandnaam geconfigureerd, configureer de bestandsnaam en start de applicatie opnieuw op"},
      {"MortgagesWindow.alertNoMortgagesFileName.header", "Geen hypotheken bestandnaam geconfigureerd"},
      {"MortgagesWindow.alertNoMortgagesFileName.content",
        "Er is geen bestandnaam geconfigureerd voor het bestand met de informatie over de hypotheken" +
        NEWLINE +
        "Configureer de bestandsnaam (bijv. door op onderstaande 'Gebruikers instellingen bewerken' knop the klikken) en start de applicatie opnieuw op." +
        NEWLINE +
        "Het opnieuw starten is nodig, omdat de instellingen alleen tijdens het opstarten van de applicatie gelezen worden."},
      {"MortgagesWindow.alertNoMortgagesFileName.editorButton", "Gebruikers instellingen bewerken"},
      {"MortgagesWindow.alertMortgagesFileNotFound.title", "Hypotheken bestand niet gevonden"},
      {"MortgagesWindow.alertMortgagesFileNotFound.header", "Het bestand met hypotheken ({0}) bestaat nog niet."},
      {"MortgagesWindow.alertMortgagesFileNotFound.content", "Wil je dit bestand nu aanmaken?" + NEWLINE +
        "Als je \"Nee\" kiest kan je in dit scherm niets doen."}
    };
  }


}
