package goedegep.media.fotoshow.app.guifx;

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
public class SelectPhotosWizard extends Dialog<ButtonType> {
  @SuppressWarnings("unused")
  private final static Logger LOGGER = Logger.getLogger(SelectPhotosWizard.class.getName());
  
  private final static String WINDOW_TITLE = "Select photos information";

  public SelectPhotosWizard(CustomizationFx customization, Stage ownerWindow) {
    setTitle(WINDOW_TITLE);
    
    initOwner(ownerWindow);
    
    createGUI(customization);
    setResizable(true);
  }
    
  private void createGUI(CustomizationFx customization) {
    WebView webView = new WebView();
    webView.getEngine().loadContent("<div style=\"background-color:" + JfxUtil.colorToCssString(customization.getLook().getPanelBackgroundColor()) + "\">" +
                                    "Double click on a photo to (de)select it for the show." +
                                    " Photos which aren't selected for the show are shown smaller.</br>" +
                                    "Sometimes you want to see some photos full screen in order to decide which ones to show." +
                                    " This can be done by selecting one or more photos in the list and then pressing the 'View selection full screen' button." +
                                    "</div>");
    
    getDialogPane().setContent(webView);

    getDialogPane().getButtonTypes().add(ButtonType.OK);    
  }
 
}
