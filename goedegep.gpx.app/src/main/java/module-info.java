module goedegep.gpx.app {
  exports goedegep.gpx.app;
  requires transitive goedegep.appgenfx;
  requires goedegep.gpx.model;
  requires org.eclipse.emf.ecore;
  requires org.eclipse.emf.ecore.xmi;
  requires javafx.graphics;
  requires com.gluonhq.maps;
  requires goedegep.geo;
  requires transitive goedegep.resources;
  requires transitive goedegep.gpx;
  requires goedegep.poi.model;
  requires goedegep.poi.app;
  requires goedegep.mapview;
  requires javafx.controls;
  requires goedegep.util;
}