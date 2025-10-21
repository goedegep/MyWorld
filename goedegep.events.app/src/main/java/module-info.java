module goedegep.events.app {
  exports goedegep.events.app;
  exports goedegep.events.app.guifx;

  requires transitive goedegep.jfx;
  requires transitive goedegep.events.model;
  requires goedegep.gpx.app;
  requires goedegep.model.properties;
  requires goedegep.myworld.common;
  requires goedegep.properties.app;
  requires goedegep.resources;
  requires goedegep.types.model;
  requires goedegep.util;
  requires java.logging;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires com.openhtmltopdf;
  requires com.openhtmltopdf.pdfboxout;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
  requires org.commonmark;
  requires javafx.web;
  requires goedegep.appgen;
}