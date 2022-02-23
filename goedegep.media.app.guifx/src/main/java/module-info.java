module goedegep.media.app.guifx {
  exports goedegep.media.app.guifx;

  requires goedegep.appgen;
  requires transitive goedegep.appgenfx;
  requires goedegep.media.app;
  requires goedegep.media.fotoshow.app;
  requires goedegep.media.mediadb.app;
  requires goedegep.media.model.mediadb;
  requires goedegep.util;
  requires java.desktop;
  requires java.logging;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires goedegep.properties.app;
  requires goedegep.media.photoshow.model;
}