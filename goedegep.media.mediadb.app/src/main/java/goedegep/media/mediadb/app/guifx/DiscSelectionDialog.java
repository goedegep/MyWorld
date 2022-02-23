package goedegep.media.mediadb.app.guifx;

import java.util.List;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.media.mediadb.app.AlbumDiscLocationInfo;
import goedegep.media.mediadb.model.Disc;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DiscSelectionDialog extends Dialog<Boolean> {
  
  private final static String WINDOW_TITLE = "Disc selection";
  
  private ComponentFactoryFx componentFactory;
  private int selectedDiscIndex = -1;

  
  public DiscSelectionDialog(CustomizationFx customization, Stage ownerWindow, List<Disc> discs, List<AlbumDiscLocationInfo> albumDiscLocationInfos, ObjectProperty<AlbumDiscLocationInfo> selectedDisc) {
    setTitle(WINDOW_TITLE);
    
    initOwner(ownerWindow);

    componentFactory = customization.getComponentFactoryFx();
    
    createGUI(discs, albumDiscLocationInfos, selectedDisc);
    setResizable(true);
  }

  private void createGUI(List<Disc> discs, List<AlbumDiscLocationInfo> albumDiscLocationInfos, ObjectProperty<AlbumDiscLocationInfo> selectedDisc) {
    setHeaderText("Select the disc to be played.");
    
    VBox vBox = componentFactory.createVBox();
    
    int discIndex = 0;
    for (Disc disc: discs) {
      String discText = null;
      if (disc.isSetTitle()) {
        discText = disc.getTitle();
      } else {
        discText = "Disc " + Integer.toString(discIndex + 1);
      }
      Button button = componentFactory.createButton(discText, null);
      button.setId(Integer.toString(discIndex));
      button.setOnAction((e) ->{
        Button actionButton = (Button) e.getSource();
        selectedDiscIndex = Integer.valueOf(actionButton.getId());
        selectedDisc.setValue(albumDiscLocationInfos.get(selectedDiscIndex));
        closeDialog();
      });
      vBox.getChildren().add(button);
      
      discIndex++;
    }
            
    getDialogPane().setContent(vBox);

  }
  
  private void closeDialog() {
    // hack: setting result is needed to let the dialog close
    setResult(Boolean.TRUE);
    close();
  }
}
