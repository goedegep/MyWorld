module goedegep.unitconverter {
  exports goedegep.unitconverter.exe;
  exports goedegep.unitconverter.svc;
  exports goedegep.unitconverter.gui;

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