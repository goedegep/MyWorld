module goedegep.pctools {
  exports goedegep.pctools.exe;
  exports goedegep.pctools.filefinder.logic;
  exports goedegep.pctools.filescontrolled.logic;
  exports goedegep.pctools.gui;
  exports goedegep.pctools.filescontrolled.guifx;
  exports goedegep.pctools.filescontrolled.types;
  exports goedegep.pctools.logic;
  exports goedegep.pctools.filefinder.guifx;

  requires transitive goedegep.jfx;
  requires goedegep.model.properties;
  requires transitive goedegep.pctools.filescontrolled.model;
  requires goedegep.properties.app;
  requires goedegep.util;
  requires java.logging;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
  requires javafx.web;
  requires tim.prune;
  requires goedegep.myworld.common;
  requires goedegep.resources;
  requires org.commonmark;
  requires com.google.common.jimfs;
  requires goedegep.model.configuration;
}