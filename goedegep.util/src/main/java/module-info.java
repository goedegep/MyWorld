module goedegep.util {
  exports goedegep.util;
  exports goedegep.util.bitsequence;
  exports goedegep.util.bytearray;
  exports goedegep.util.collections;
  exports goedegep.util.csvfileaccess;
  exports goedegep.util.datetime;
  exports goedegep.util.dir;
  exports goedegep.util.douglaspeuckerreducer;
  exports goedegep.util.emf;
  exports goedegep.util.chart;
  exports goedegep.util.clazz;
  exports goedegep.util.desktop;
  exports goedegep.util.file;
  exports goedegep.util.finance;
  exports goedegep.util.fixedpointvalue;
  exports goedegep.util.html;
  exports goedegep.util.http;
  exports goedegep.util.i18n;
  exports goedegep.util.img;
  exports goedegep.util.img.collageimpl;
  exports goedegep.util.listener;
  exports goedegep.util.logging;
  exports goedegep.util.math;
  exports goedegep.util.money;
  exports goedegep.util.mslinks;
  exports goedegep.util.mslinks.data;
  exports goedegep.util.mslinks.extra;
  exports goedegep.util.mslinks.io;
  exports goedegep.util.multibyteinteger;
  exports goedegep.util.object;
  exports goedegep.util.objectselector;
  exports goedegep.util.projectinfo;
  exports goedegep.util.sax;
  exports goedegep.util.sgml;
  exports goedegep.util.string;
  exports goedegep.util.text;
  exports goedegep.util.thread;
  exports goedegep.util.tree;
  exports goedegep.util.unit;
  exports goedegep.util.url;
  exports goedegep.util.xml;
  exports goedegep.util.xtree;
  exports goedegep.util.xtree.impl;
  exports goedegep.util.xtree.impl.ascii;
  exports goedegep.util.xtree.impl.binary;
  exports goedegep.util.xtree.impl.defaultmutable;
  exports goedegep.util.xtree.mutable;
  exports goedegep.util.xtree.nodebased;
  exports goedegep.util.xtree.serialized;
  
  requires transitive goedegep.geo;
  requires transitive java.desktop;
  requires transitive java.logging;
  requires java.prefs;
  requires java.sql;
  requires java.xml;
  requires net.iakovlev.timeshape;
  requires transitive org.apache.commons.imaging;
  requires transitive org.eclipse.emf.common;
  requires transitive org.eclipse.emf.ecore;
  requires transitive javafx.base;
  requires org.eclipse.emf.ecore.xmi;
  requires transitive com.google.common.geometry;
  requires transitive javafx.graphics;
  
  requires goedegep.emfsample.model;
  requires java.base;
  requires java.management;
}