module goedegep.unitconverter.app {
  exports goedegep.unitconverter.app;
  exports goedegep.unitconverter.app.guifx;

  requires goedegep.appgen;
  requires transitive goedegep.appgenfx;
  requires goedegep.model.properties;
  requires transitive goedegep.util;
  requires java.logging;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
}