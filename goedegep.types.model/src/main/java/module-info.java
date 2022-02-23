module goedegep.types.model {
  exports goedegep.types.model;
  exports goedegep.types.model.util;
  exports goedegep.types.model.impl;

  requires transitive goedegep.util;
  requires transitive goedegep.geo;
  requires java.desktop;
  requires java.logging;
  requires transitive org.eclipse.emf.common;
  requires transitive org.eclipse.emf.ecore;
  requires transitive javafx.graphics;
}