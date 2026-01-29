module goedegep.media {
  exports goedegep.media.exe;
  exports goedegep.media.svc;
  exports goedegep.media.app.guifx;
  exports goedegep.media.app.logic;
  exports goedegep.media.photo.photoshow.guifx;

  requires transitive goedegep.jfx;
  requires transitive goedegep.media.mediadb.model;
  requires java.logging;
  requires goedegep.properties.app;
  requires goedegep.myworld.common;
  requires goedegep.model.configuration;
  requires goedegep.resources;
  requires ealvatag;
  requires com.google.common;
  requires mp3agic;
  requires org.apache.commons.text;
  requires org.apache.commons.io;
  requires javafx.swing;
  requires com.gluonhq.maps;
  requires goedegep.mapview;
  requires goedegep.media.photoshow.model;
}