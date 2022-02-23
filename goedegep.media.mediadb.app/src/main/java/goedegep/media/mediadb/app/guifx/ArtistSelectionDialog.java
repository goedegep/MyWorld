package goedegep.media.mediadb.app.guifx;

import java.util.List;
import java.util.logging.Logger;

import goedegep.jfx.ComponentFactoryFx;
import goedegep.jfx.CustomizationFx;
import goedegep.media.mediadb.model.Artist;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ArtistSelectionDialog extends Dialog<ButtonType> {
  private final static Logger LOGGER = Logger.getLogger(ArtistSelectionDialog.class.getName());
  private final static String WINDOW_TITLE = "Artist selection";
  
  private ComponentFactoryFx componentFactory;
  private List<Artist> artists;
  private ComboBox<Artist> artistComboBox;
  
  public ArtistSelectionDialog(CustomizationFx customization, Stage ownerWindow, List<Artist> artists) {
    
    setTitle(WINDOW_TITLE);
    
    initOwner(ownerWindow);

    componentFactory = customization.getComponentFactoryFx();
    this.artists = artists;
    
    createGUI();
    setResizable(true);
  }
  
  public Artist getSelectedArtist() {
    Artist artist = artistComboBox.getValue();
    LOGGER.severe("<= " + (artist != null ? artist.getName() : "(null)"));
    return artist;
  }

  private void createGUI() {
    setHeaderText("Select the artist for whom the AlbumInfo file is to be saved.");
    
    GridPane gridPane = componentFactory.createGridPane();
    gridPane.setHgap(12.0);
    
    // Row 0: "Artist:" <artist-combobox>
    Label artistLabel = componentFactory.createLabel("Artist:");
    gridPane.add(artistLabel, 0, 0);
    
    artistComboBox = componentFactory.createComboBox(artists);
    gridPane.add(artistComboBox, 1, 0);
            
    getDialogPane().setContent(gridPane);

    getDialogPane().getButtonTypes().add(ButtonType.OK);
    getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
  }
}
