module goedegep.appgen {
  exports goedegep.appgen.eobjectsexamplemodel.impl;
  exports goedegep.appgen.checkboxtree;
  exports goedegep.appgen.swing;
  exports goedegep.appgen.controls;
  exports goedegep.appgen.eobjecttable;
  exports goedegep.appgen;
  exports goedegep.appgen.eobjectsexamplemodel;

  requires commons.cli;
  requires commons.exec;
  requires transitive goedegep.model.configuration;
  requires goedegep.model.properties;
  requires transitive goedegep.types.model;
  requires transitive goedegep.util;
  requires java.datatransfer;
  requires transitive java.desktop;
  requires java.logging;
  requires transitive javafx.base;
  requires transitive org.eclipse.emf.common;
  requires transitive org.eclipse.emf.ecore;
  requires org.eclipse.emf.ecore.xmi;
  requires transitive goedegep.resources;
}