module goedegep.gpxeditor {
  exports goedegep.gpxeditor.exe;
  exports goedegep.gpxeditor.logic;
  exports goedegep.gpxeditor.gui;
  exports goedegep.gpxeditor.svc;
  
  requires transitive goedegep.jfx;
  requires goedegep.gpx.model;
  requires goedegep.gpx10.model;
  requires org.eclipse.emf.ecore;
  requires org.eclipse.emf.ecore.xmi;
  requires javafx.graphics;
  requires com.gluonhq.maps;
  requires goedegep.geo;
  requires transitive goedegep.resources;
  requires transitive goedegep.gpx;
  requires goedegep.poi;
  requires goedegep.mapview;
  requires goedegep.myworld.common;
  requires javafx.controls;
  requires goedegep.util;
  requires javafx.base;
  requires tim.prune;
  requires goedegep.model.configuration;
  requires org.apache.commons.cli;
}