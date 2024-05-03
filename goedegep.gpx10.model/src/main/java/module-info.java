module goedegep.gpx10.model {
  exports goedegep.gpx10.model;
  exports goedegep.gpx10.model.util;
  exports goedegep.gpx10.model.impl;

  requires transitive java.xml;
  requires transitive org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
  requires org.eclipse.emf.ecore.xmi;
}