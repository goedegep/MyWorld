module goedegep.media.app {
  exports goedegep.media.app;
  exports goedegep.media.app.base;

  requires goedegep.appgen;
  requires goedegep.appgenfx;
  requires transitive goedegep.media.model.mediadb;
  requires transitive goedegep.model.properties;
  requires goedegep.util;
  requires java.desktop;
  requires java.logging;
  requires javafx.base;
  requires transitive javafx.graphics;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
}