module goedegep.finan.nota.app {
  exports goedegep.invandprop.app;
  exports goedegep.invandprop.app.guifx;

//  requires goedegep.app.finan;
  requires goedegep.appgen;
  requires transitive goedegep.jfx;
  requires transitive goedegep.invandprop.model;
  requires transitive goedegep.model.properties;
  requires transitive goedegep.types.model;
  requires goedegep.properties.app;
  requires goedegep.util;
  requires java.datatransfer;
  requires java.desktop;
  requires java.logging;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
  requires org.eclipse.emf.ecore.xmi;
  requires goedegep.resources;
}