module org.apache.pdfbox {
  exports org.apache.pdfbox;
  exports org.apache.pdfbox.cos;
  exports org.apache.pdfbox.multipdf;
  exports org.apache.pdfbox.pdmodel;
  exports org.apache.pdfbox.pdmodel.common;
  exports org.apache.pdfbox.pdmodel.common.filespecification;
  exports org.apache.pdfbox.pdmodel.common.function;
  exports org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure;
  exports org.apache.pdfbox.pdmodel.documentinterchange.markedcontent;
  exports org.apache.pdfbox.pdmodel.documentinterchange.taggedpdf;
  exports org.apache.pdfbox.pdmodel.encryption;
  exports org.apache.pdfbox.pdmodel.font;
  exports org.apache.pdfbox.pdmodel.graphics;
  exports org.apache.pdfbox.pdmodel.graphics.color;
  exports org.apache.pdfbox.pdmodel.graphics.form;
  exports org.apache.pdfbox.pdmodel.graphics.image;
  exports org.apache.pdfbox.pdmodel.graphics.pattern;
  exports org.apache.pdfbox.pdmodel.graphics.shading;
  exports org.apache.pdfbox.pdmodel.graphics.state;
  exports org.apache.pdfbox.pdmodel.interactive.action;
  exports org.apache.pdfbox.pdmodel.interactive.annotation;
  exports org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination;
  exports org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline;
  exports org.apache.pdfbox.pdmodel.interactive.form;
  exports org.apache.pdfbox.pdmodel.interactive.viewerpreferences;
  exports org.apache.pdfbox.rendering;
  exports org.apache.pdfbox.util;
  
  requires java.base;
  requires java.desktop;
  requires org.apache.pdfbox.fontbox;
  requires org.apache.pdfbox.io;
  requires org.apache.logging.log4j;
  requires java.logging;
}