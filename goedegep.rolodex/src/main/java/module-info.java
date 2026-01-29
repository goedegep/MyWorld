module goedegep.rolodex {
  exports goedegep.rolodex.exe;
  exports goedegep.rolodex.logic;
  exports goedegep.rolodex.gui;
  exports goedegep.rolodex.svc;
  
  opens goedegep.rolodex.svc;
  opens goedegep.rolodex.gui;
  
  requires transitive goedegep.jfx;
  requires goedegep.model.configuration;
  requires goedegep.model.properties;
  requires transitive goedegep.model.rolodex;
  requires goedegep.properties.app;
  requires goedegep.util;
  requires java.desktop;
  requires java.logging;
  requires java.sql;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
  requires goedegep.myworld.common;
  requires goedegep.resources;
  requires com.openhtmltopdf;
  requires com.openhtmltopdf.pdfboxout;
  requires org.apache.commons.io;
}