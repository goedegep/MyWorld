package goedegep.media.photo.photoshow.guifx;

import java.util.logging.Logger;

import goedegep.jfx.CustomizationFx;
import goedegep.jfx.JfxUtil;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * This class shows a dialog with an informational text about how to organize photos.
 * <p>
 * So this isn't a real 'wizard'.
 */
public class OrganizePhotosWizard extends Dialog<ButtonType> {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(OrganizePhotosWizard.class.getName());
  
  private final static String WINDOW_TITLE = "Organize photos in folders wizard";

  /**
   * Constructor
   * 
   * @param customization the window customization
   * @param ownerWindow the window which owns (created) this wizard.
   */
  public OrganizePhotosWizard(CustomizationFx customization, Stage ownerWindow) {
    setTitle(WINDOW_TITLE);
    
    initOwner(ownerWindow);
    
    createGUI(customization);
    setResizable(true);
  }
    
  /*
   * Create the GUI.
   */
  private void createGUI(CustomizationFx customization) {
    WebView webView = new WebView();
    webView.getEngine().loadContent("<div style=\"background-color:" + JfxUtil.colorToCssString(customization.getLook().getPanelBackgroundColor()) + "\">" +
                                    "This step, organizing your photos, is to be done outside of this program.</br>" +
                                    "Create a folder with the date and name of the 'event' (typically some holidays).</br>" +
                                    "For each device from which you got photos, create a sub folder with the name of that device." +
                                    "Put the photos of this device in this sub folder.<br/>" +
                                    "Example:<br/>" +
                                    "<ul>" +
                                      "<li>C:\\photo</li>" +
                                      "<ul>" +
                                        "<li>Vacations</li>" +
                                        "<ul>" +
                                          "<li>2018-08-29 The south-east of the Netherlands</li>" +
                                          "<ul>" +
                                            "<li>Galaxy S6</li>" +
                                            "<ul>" +
                                              "<li>201808-30_172945.jpg</li>" +
                                              "<li>...</li>" +
                                            "</ul>" +
                                            "<li>Lumix</li>" +
                                            "<ul>" +
                                            "<li>P1020822.JPG.jpg</li>" +
                                            "<li>...</li>" +
                                          "</ul>" +
                                          "</ul>" +
                                        "</ul>" +
                                      "</ul>" +
                                    "</ul>" +
                                    "</div>");
    
    getDialogPane().setContent(webView);

    getDialogPane().getButtonTypes().add(ButtonType.OK);    
  }
 
}
