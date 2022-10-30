package goedegep.vacations.app;


import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxUtil;
import goedegep.poi.app.guifx.POIIcons;
import goedegep.vacations.app.logic.VacationToHtmlConverter;
import goedegep.vacations.model.Location;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * This class shows a dialog with an informational text about how to organize photos.
 * <p>
 * So this isn't a real 'wizard'.
 */
public class LocationDescriptionDialog extends Dialog<ButtonType> {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(LocationDescriptionDialog.class.getName());
  
  private static POIIcons poiIcons;
  private static VacationToHtmlConverter vacationToHtmlConverter;
  
  public static void setPoiIcons(POIIcons poiIcons) {
    LocationDescriptionDialog.poiIcons = poiIcons;
    vacationToHtmlConverter = new VacationToHtmlConverter(poiIcons);
  }

  /**
   * Constructor
   * 
   * @param customization the window customization
   * @param ownerWindow the window which owns (created) this wizard.
   */
  public LocationDescriptionDialog(CustomizationFx customization, Stage ownerWindow, Location location) {
    setTitle("Informatie over " + location.getName());
    Image locationIcon = null;
    if (location.isSetLocationType()) {
      locationIcon = poiIcons.getIcon(location.getLocationType());
    }
    if (locationIcon != null) {
      setGraphic(new ImageView(locationIcon));
    }
    
    initOwner(ownerWindow);
    
    createGUI(customization, ownerWindow, location);
    setResizable(true);
  }
    
  /*
   * Create the GUI.
   */
  private void createGUI(CustomizationFx customization, Stage ownerWindow, Location location) {
    WebView webView = new WebView();
    String description = vacationToHtmlConverter.LocationToHtml(location);
    webView.getEngine().loadContent("<div style=\"background-color:" + JfxUtil.colorToCssString(customization.getLook().getPanelBackgroundColor()) + "\">" +
                                    description +
                                    "</div>");
    
    getDialogPane().setContent(webView);

    getDialogPane().getButtonTypes().add(ButtonType.OK);    
  }
 
}
