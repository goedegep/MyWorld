module goedegep.poi {
  exports goedegep.poi.app;

  requires transitive goedegep.resources;
  requires goedegep.util;
  requires java.logging;
  requires javafx.base;
  requires transitive javafx.graphics;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
}