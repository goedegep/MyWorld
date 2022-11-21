module goedegep.media.fotoshow.app {
  exports goedegep.media.fotomapview.guifx;
  exports goedegep.media.fotoshow.app;
  exports goedegep.media.fotoshow.app.guifx;
  exports goedegep.media.photo;

  requires goedegep.appgen;
  requires transitive goedegep.appgenfx;
  requires transitive goedegep.geo;
  requires goedegep.media.app;
  requires transitive goedegep.media.photoshow.model;
  requires goedegep.model.configuration;
  requires goedegep.util;
  requires java.desktop;
  requires java.logging;
  requires javafx.base;
  requires javafx.controls;
  requires transitive javafx.graphics;
  requires javafx.swing;
  requires javafx.web;
  requires org.apache.commons.imaging;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
  requires transitive com.gluonhq.maps;
  requires goedegep.resources;
  requires transitive com.google.common.geometry;
  requires goedegep.mapview;
}