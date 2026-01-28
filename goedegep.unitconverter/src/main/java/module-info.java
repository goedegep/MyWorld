module goedegep.unitconverter.app {
  exports goedegep.unitconverter.app;
  exports goedegep.unitconverter.app.guifx;

  requires transitive goedegep.jfx;
  requires goedegep.model.properties;
  requires goedegep.properties.app;
  requires transitive goedegep.util;
  requires java.logging;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
  requires goedegep.myworld.common;
  requires goedegep.resources;
  requires goedegep.model.configuration;
}