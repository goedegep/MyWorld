module goedegep.mapview {
  exports goedegep.mapview.image;
  exports goedegep.mapview.maptile;
  exports goedegep.mapview.maptile.osm;
  exports goedegep.mapview;
  exports goedegep.mapview.image.impl;
  exports goedegep.mapview.view;
  exports goedegep.mapview.impl;
  
  requires transitive goedegep.geo;
  requires java.logging;
  requires javafx.base;
  requires transitive javafx.graphics;
}