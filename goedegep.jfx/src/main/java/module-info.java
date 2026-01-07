module goedegep.jfx {
  exports goedegep.jfx;
  exports goedegep.jfx.browser;
  exports goedegep.jfx.collage;
  exports goedegep.jfx.collections;
  exports goedegep.jfx.controls;
  exports goedegep.jfx.editor;
  exports goedegep.jfx.editor.controls;
  exports goedegep.jfx.editor.panels;
  exports goedegep.jfx.eobjecteditor;
  exports goedegep.jfx.eobjecttable;
  exports goedegep.jfx.eobjecttreeview;
  exports goedegep.jfx.objectcontrols;
  exports goedegep.jfx.objecteditor;
  exports goedegep.jfx.observableelist;
  exports goedegep.jfx.img;
  exports goedegep.jfx.stringconverterandchecker;
  exports goedegep.jfx.treeview;
  exports goedegep.jfx.workerstategui;
  exports goedegep.jfx.xtreeview;

  requires org.apache.commons.cli;
  requires goedegep.model.configuration;
  requires transitive goedegep.model.properties;
  requires transitive goedegep.util;
  requires java.desktop;
  requires java.logging;
  requires javafx.base;
  requires transitive javafx.controls;
  requires javafx.graphics;
  requires javafx.swing;
  requires transitive javafx.web;
  requires org.commonmark;
  requires org.eclipse.emf.common;
  requires transitive org.eclipse.emf.ecore;
  requires transitive goedegep.types.model;
  requires goedegep.geo;
  requires goedegep.resources;
  
  opens goedegep.jfx.eobjecttable;
}