module goedegep.invandprop {
  exports goedegep.invandprop.exe;
  exports goedegep.invandprop.logic;
  exports goedegep.invandprop.gui;

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
  requires goedegep.myworld.common;
  requires goedegep.resources;
  requires goedegep.model.configuration;
}