package goedegep.media.mediadb.gui;

import java.util.List;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.media.mediadb.model.Artist;
import goedegep.media.mediadb.model.Disc;
import goedegep.media.musicfolder.logic.AlbumOnHardDiskInfo;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class is a {@code Dialog} for selecting a {@code Disc} from a multi-disc album.
 * <p>
 * If a user selects a multi-disc album to be played, he has to select the disc to be played.
 */
public class DiscSelectionDialog extends Dialog<Boolean> {
  
  private final static String WINDOW_TITLE = "Disc selection";
  
  private ComponentFactoryFx componentFactory;
  private int selectedDiscIndex = -1;

  
  /**
   * Constructor.
   * 
   * @param customization The GUI customization.
   * @param ownerWindow The window owning this dialog
   * @param discs the list of {@code Disc}s to chose from
   * @param albumDiscLocationInfos
   * @param selectedDisc
   */
  public DiscSelectionDialog(CustomizationFx customization, Stage ownerWindow, List<Disc> discs, List<AlbumOnHardDiskInfo> albumDiscLocationInfos, ObjectProperty<AlbumOnHardDiskInfo> selectedDisc) {
    setTitle(WINDOW_TITLE);
    
    initOwner(ownerWindow);

    componentFactory = customization.getComponentFactoryFx();
    
    createGUI(discs, albumDiscLocationInfos, selectedDisc);
    setResizable(true);
  }

  /**
   * Create the GUI.
   * 
   * @param discs the {@code Disc}s of the album.
   * @param albumDiscLocationInfos a list of {@code AlbumOnHardDiskInfo} related to the {@code discs}.
   * @param selectedDisc an {@code ObjectProperty} used as an output parameter for the selected disc.
   */
  private void createGUI(List<Disc> discs, List<AlbumOnHardDiskInfo> albumDiscLocationInfos, ObjectProperty<AlbumOnHardDiskInfo> selectedDisc) {
    setWidth(500);
    setHeight(350);
    
    StringBuilder buf = new StringBuilder();
    buf.append("Which disc of the album\n");
    AlbumOnHardDiskInfo albumOnHardDiskInfo = albumDiscLocationInfos.getFirst();
    String artist = albumOnHardDiskInfo.artist();
    if (artist != null) {
      buf.append(artist).append(" - ");
    }
    buf.append(albumOnHardDiskInfo.title()).append("\ndo you want to hear?");
    
    setHeaderText(buf.toString());
    
    VBox vBox = componentFactory.createVBox(12.0, 12.0);
    
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
      button.setOnAction((e) -> {
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
  
  /**
   * Close the Dialog
   */
  private void closeDialog() {
    // hack: setting result is needed to let the dialog close
    setResult(Boolean.TRUE);
    close();
  }
}
