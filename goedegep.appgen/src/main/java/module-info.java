module goedegep.appgen {
  exports goedegep.appgen.eobjectsexamplemodel.impl;
  exports goedegep.appgen.checkboxtree;
  exports goedegep.appgen.swing;
  exports goedegep.appgen.controls;
  exports goedegep.appgen.eobjecttable;
  exports goedegep.appgen;
  exports goedegep.appgen.eobjectsexamplemodel;

  requires org.apache.commons.cli;
  requires transitive goedegep.model.configuration;
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