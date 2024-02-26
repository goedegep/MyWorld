module goedegep.gpx.model {
  exports goedegep.gpx.model.util;
  exports goedegep.gpx.model;

  requires transitive java.xml;
  requires transitive org.eclipse.emf.common;
  requires transitive org.eclipse.emf.ecore;
  requires org.eclipse.emf.ecore.xmi;
  requires goedegep.geo;
  requires java.logging;
  requires goedegep.util;
}