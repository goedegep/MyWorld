module goedegep.appgenfx {
  exports goedegep.jfx;
  exports goedegep.jfx.browser;
  exports goedegep.jfx.collage;
  exports goedegep.jfx.collections;
  exports goedegep.jfx.controls;
  exports goedegep.jfx.eobjecteditor;
  exports goedegep.jfx.eobjecttable;
  exports goedegep.jfx.eobjecttable.objectstringconverters;
  exports goedegep.jfx.eobjecttreeview;
  exports goedegep.jfx.observableelist;
  exports goedegep.jfx.img;
  exports goedegep.jfx.jfxjunitrunner;
  exports goedegep.jfx.stringconverters;
  exports goedegep.jfx.treeview;
  exports goedegep.jfx.workerstategui;
  exports goedegep.jfx.xtreeview;

  requires commons.cli;
  requires transitive goedegep.appgen;
  requires goedegep.model.configuration;
  requires transitive goedegep.model.properties;
  requires transitive goedegep.util;
  requires java.desktop;
  requires java.logging;
  requires javafx.base;
  requires transitive javafx.controls;
  requires javafx.graphics;
  requires javafx.web;
  requires org.commonmark;
  requires org.eclipse.emf.common;
  requires transitive org.eclipse.emf.ecore;
  requires ljv;
  requires transitive goedegep.types.model;
  requires javafx.swing;
  requires goedegep.geo;
  requires goedegep.resources;
  requires junit;
  requires org.junit.jupiter.api;
  
  opens goedegep.jfx.eobjecttable;
}