module goedegep.rolodex.app {
  exports goedegep.rolodex.app.guifx;
  exports goedegep.rolodex.app;
  
  opens goedegep.rolodex.app;
  opens goedegep.rolodex.app.guifx;
  
//  requires commons.logging;
  requires goedegep.appgen;
  requires transitive goedegep.appgenfx;
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
  requires junit;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
//  requires ljv;
  requires goedegep.resources;
}