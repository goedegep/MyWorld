module goedegep.myworld {
  exports goedegep.myworld;
  exports goedegep.myworld.app;
  exports goedegep.myworld.app.guifx;
  
  requires commons.cli;
  requires goedegep.app.finan;
  requires goedegep.appgen;
  requires transitive goedegep.appgenfx;
  requires goedegep.finan.nota.app;
  requires goedegep.media.app;
  requires transitive goedegep.model.properties;
  requires goedegep.model.rolodex;
  requires goedegep.pctools;
  requires goedegep.properties.app;
  requires goedegep.rolodex.app;
  requires goedegep.util;   // Strange that this is needed.
  requires goedegep.unitconverter.app;
  requires goedegep.vacations.app;
  requires java.logging;
  requires javafx.controls;
  requires transitive javafx.graphics;
  requires goedegep.media.app.guifx;
  requires java.desktop;
  requires goedegep.resources;
  requires goedegep.events.app; 
}