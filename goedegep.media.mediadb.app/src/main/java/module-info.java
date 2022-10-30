module goedegep.media.mediadb.app {
  exports goedegep.media.mediadb.app;
  exports goedegep.media.mediadb.app.guifx;
  exports goedegep.media.mediadb.albuminfo;
  exports goedegep.media.musicfolder;

  requires com.google.common;
  requires goedegep.appgen;
  requires transitive goedegep.appgenfx;
  requires goedegep.media.app;
  requires transitive goedegep.media.mediadb.model;
  requires goedegep.model.configuration;
  requires goedegep.model.properties;
  requires goedegep.properties.app;
  requires transitive goedegep.util;
  requires java.desktop;
  requires java.logging;
  requires java.xml;
  requires javafx.base;
  requires javafx.controls;
  requires transitive javafx.graphics;
  requires javafx.web;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
  requires java.base;
  requires mp3agic;
  requires ealvatag;
  requires javaFlacEncoder;
  requires org.apache.commons.text;
  requires goedegep.resources;
}