module goedegep.poi.app {
  exports goedegep.poi.app.guifx;

  requires goedegep.appgen;
  requires transitive goedegep.poi.model;
  requires goedegep.util;
  requires java.logging;
  requires javafx.base;
  requires transitive javafx.graphics;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
}