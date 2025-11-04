module goedegep.media.app {
  exports goedegep.media.app;
  exports goedegep.media.app.guifx;
  exports goedegep.media.app.logic;

  requires transitive goedegep.jfx;
  requires transitive goedegep.media.mediadb.model;
  requires java.logging;
  requires goedegep.properties.app;
  requires transitive goedegep.media.common;
  requires goedegep.media.mediadb.app;
  requires goedegep.myworld.common;
}