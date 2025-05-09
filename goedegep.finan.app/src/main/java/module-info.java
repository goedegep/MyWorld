module goedegep.app.finan {
  exports goedegep.app.finan.abnamrobank.guifx;
  exports goedegep.app.finan.postbankapp;
  exports goedegep.finan.abnamrobank.aaeffrek;
  exports goedegep.finan.investmentinsurances;
  exports goedegep.finan.direktbank.direktsprek;
  exports goedegep.app.finan.stocksapp.guifx;
  exports goedegep.finan.jobappointment;
  exports goedegep.app.finan.abnamrobank;
  exports goedegep.app.finan.gen;
  exports goedegep.app.finan.td;
  exports goedegep.app.finan.finanapp.guifx;
  exports goedegep.finan.direktbank;
  exports goedegep.app.finan.guifx;
  exports goedegep.app.finan.finanapp.td;
  exports goedegep.finan.postbank.pbbasic;
  exports goedegep.finan.stocks;
  exports goedegep.finan.jobappointment.guifx;
  exports goedegep.finan.postbank.pbrek;
  exports goedegep.finan.abnamrobank;
  exports goedegep.app.finan.direktbankapp.guifx;
  exports goedegep.finan.basic;
  exports goedegep.finan.postbank.pbeffrek;
  exports goedegep.app.finan.effrek;
  exports goedegep.finan.pensioen.nn;
  exports goedegep.app.finan.finanapp;
  exports goedegep.app.finan.stocksapp;
  exports goedegep.app.finan.postbankapp.guifx;
  exports goedegep.finan.postbank.pbsprek;
  exports goedegep.app.finan.registry;
  exports goedegep.app.finan.direktbankapp;
  exports goedegep.finan.postbank.pbfonds;
  exports goedegep.app.finan.koopwoning;
  exports goedegep.finan.mortgage;
  exports goedegep.finan.effrek;
  exports goedegep.app.finan.overzichten;
  exports goedegep.finan.mortgage.app.guifx;
  exports goedegep.finan;
  exports goedegep.finan.investmentinsurances.app.guifx;
    
  requires transitive goedegep.appgen;
  requires transitive goedegep.appgenfx;
  requires goedegep.model.configuration;
  requires transitive goedegep.model.properties;
  requires transitive goedegep.model.rolodex;
  requires goedegep.types.model;
  requires goedegep.properties.app;
  requires goedegep.rolodex.app;
  requires transitive goedegep.util;
//  requires itextpdf;
  requires transitive java.desktop;
  requires java.base;
  requires java.logging;
  requires transitive java.xml;
  requires javafx.base;
  requires javafx.controls;
  requires javafx.graphics;
  requires jcommon;
  requires jfreechart;
  requires org.apache.pdfbox;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
  requires org.eclipse.emf.ecore.xmi;
  requires transitive goedegep.finan.mortgage.model;
  requires transitive goedegep.finan.investmentinsurance.model;
  requires transitive goedegep.finan.lynx2finan.model;
  requires transitive goedegep.finan.jobappointment.model;
  requires goedegep.resources;
}