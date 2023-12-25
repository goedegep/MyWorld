package goedegep.vacations.app.guifx;

import java.util.function.Supplier;
import java.util.logging.Logger;

import com.gluonhq.maps.MapLayer;

import goedegep.jfx.CustomizationFx;
import goedegep.resources.ImageSize;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ControlsLayer extends MapLayer {
  @SuppressWarnings("unused")
  private static final Logger LOGGER = Logger.getLogger(ControlsLayer.class.getName());
    
  public ControlsLayer(CustomizationFx customization, Supplier<LocationSearchWindow> searchWindowSupplier) {
    
    Node searchIcon = new ImageView(TravelImageResource.SEARCH.getIcon(ImageSize.SIZE_2));
    searchIcon.setTranslateX(30);
    searchIcon.setTranslateY(30);
    searchIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent arg0) {
        searchWindowSupplier.get();
      }
      
    });
   
    getChildren().add(searchIcon);
  }
}
