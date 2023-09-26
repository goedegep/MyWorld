module goedegep.gpx {
  exports goedegep.gpx;
  
  requires transitive java.xml;
  requires transitive goedegep.geo;
  requires java.logging;
  requires transitive goedegep.util;
  requires transitive goedegep.gpx.model;
  requires transitive goedegep.gpx10.model;
  requires javafx.base;
  requires org.eclipse.emf.ecore;
}