module goedegep.mapview {
  exports goedegep.mapview.image;
  exports goedegep.mapview.maptile;
  exports goedegep.mapview.maptile.osm;
  exports goedegep.mapview;
  exports goedegep.mapview.image.impl;
  exports goedegep.mapview.view;
  exports goedegep.mapview.impl;
  exports goedegep.gluonhq.maps;
  
  uses goedegep.gluonhq.maps.tile.TileRetriever;

  requires transitive com.gluonhq.maps;
  requires transitive goedegep.geo;
  requires java.logging;
  requires javafx.base;
  requires javafx.graphics;
}