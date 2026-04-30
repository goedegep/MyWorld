package goedegep.mapview.image.impl;

import goedegep.mapview.impl.MapTileImageViewAbstract;
import javafx.scene.image.Image;

public class MapTileImageView extends MapTileImageViewAbstract {
  MapTileImageView(BaseMap baseMap, int myZoom, long i, long j) {
    super(baseMap, myZoom, i, j);

    try {
      Image image = TILE_RETRIEVER.loadTileSynchronously(myZoom, i, j);
      image = addTileParametersToImage(image, myZoom, i, j);
      setImage(image);
    } catch (Exception e) {
      setImage(null);
    }


  //    setNeedsLayout(true);

  //    calculatePosition();
}

}
