module goedegep.myworld {
  exports goedegep.myworld;
  exports goedegep.myworld.app;
  exports goedegep.myworld.app.guifx;
  
  requires org.apache.commons.cli;
  requires goedegep.appgen;
  requires transitive goedegep.appgenfx;
  requires transitive goedegep.model.properties;
  requires goedegep.pctools;
  requires goedegep.properties.app;
  requires goedegep.util;   // Strange that this is needed.
  requires goedegep.unitconverter.app;
  requires java.logging;
  requires javafx.controls;
  requires transitive javafx.graphics;
  requires java.desktop;
  requires goedegep.resources;
  requires javafx.base;
}