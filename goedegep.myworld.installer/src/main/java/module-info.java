module goedegep.myworld.installer {
  exports goedegep.myworld.installer;
  
  requires goedegep.appgen;
  requires goedegep.appgenfx;
  requires goedegep.model.properties;
  requires goedegep.util;
  requires java.logging;
  requires javafx.controls;
  requires transitive javafx.graphics;
  requires javafx.web;
  requires javafx.base;
  requires org.eclipse.emf.ecore;
  requires org.apache.commons.io;
}