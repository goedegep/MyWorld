module goedegep.properties.app {
  exports goedegep.properties.app;
  exports goedegep.properties.app.guifx;

  requires goedegep.appgen;
  requires transitive goedegep.jfx;
  requires transitive goedegep.model.properties;
  requires goedegep.util;
  requires java.desktop;
  requires java.logging;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
}