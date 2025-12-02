package goedegep.travels.app.guifx;


import javafx.concurrent.Task;
import javafx.scene.Scene;

public class MapImageViewProgressTask extends Task<Boolean> {
  
  private TravelMapView imageTravelMapView;
  private Scene scene;
  private boolean layoutDone = false;
  
  private boolean ready = false;

  public MapImageViewProgressTask(TravelMapView imageTravelMapView, Scene scene) {
    this.imageTravelMapView = imageTravelMapView;
    this.scene = scene;
  }

  @Override
  protected Boolean call() throws Exception {
    System.out.println("MapImageViewProgressTask started");
//    scene.addPostLayoutPulseListener(() -> layoutDone = true);
    while (!ready()) {
      Thread.sleep(4000);
      System.out.println("layoutDone = " + layoutDone + ", allTilesAvailable = " + imageTravelMapView.getBaseMap().allTilesAvailable());
      ready = true;
    }
    updateValue(true);
    
    return true;
  }

  // The map is ready when:
  // - all the map tiles are loaded
  // - the map isn't dirty (i.e. it doesn't need to be redrawn)       
  // - JavaFX has done a layout pass
  private boolean ready() {
    if (ready) {
      return true;
    }
    
    if (imageTravelMapView.getBaseMap().allTilesAvailable()  && layoutDone) {
      return true;
    }
    return false;
  }

}
