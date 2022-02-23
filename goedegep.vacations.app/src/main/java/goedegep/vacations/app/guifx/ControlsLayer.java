package goedegep.vacations.app.guifx;

import java.io.InputStream;
import java.util.logging.Logger;

import com.atlis.location.nominatim.NominatimAPI;
import com.gluonhq.maps.MapLayer;

import goedegep.jfx.CustomizationFx;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ControlsLayer extends MapLayer {
  private static final Logger LOGGER = Logger.getLogger(ControlsLayer.class.getName());
    
  public ControlsLayer(CustomizationFx customization, VacationsWindow vacationsWindow, NominatimAPI nominatimAPI, SearchResultLayer searchResultLayer) {
    
    Node searchIcon = createSearchIcon();
    searchIcon.setTranslateX(30);
    searchIcon.setTranslateY(30);
    searchIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent arg0) {
        LocationSearchWindow locationSearchWindow = new LocationSearchWindow(customization, vacationsWindow, nominatimAPI, searchResultLayer);
        locationSearchWindow.show();
      }
      
    });
   
    getChildren().add(searchIcon);
  }

  private ImageView createSearchIcon() {
    Image icon = null;
    String iconFileName = "search.png";
    InputStream iconInputStream = ControlsLayer.class.getResourceAsStream(iconFileName);
    if (iconInputStream == null) {
      LOGGER.severe("Icon file doesn't seem to exist: " + iconFileName);
      return null;        
    }
    icon = new Image(iconInputStream, 48, 48, true, true);
    
    return new ImageView(icon);
  }
}
