module goedegep.model.properties {
  exports goedegep.properties.model.util;
  exports goedegep.properties.model;

  requires java.logging;
  requires transitive org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
}