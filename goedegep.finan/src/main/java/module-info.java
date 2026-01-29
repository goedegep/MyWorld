module goedegep.finan {
  exports goedegep.finan.investmentinsurances.logic;
  exports goedegep.finan.jobappointment.logic;
  exports goedegep.finan.gui;
  exports goedegep.finan.jobappointment.gui;
  exports goedegep.finan.pensioen.nn;
  exports goedegep.finan.mortgage.logic;
  exports goedegep.finan.mortgage.gui;
  exports goedegep.finan.investmentinsurances.gui;
    
  requires transitive goedegep.jfx;
  requires java.logging;
  requires org.apache.pdfbox;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
  requires org.eclipse.emf.ecore.xmi;
  requires transitive goedegep.finan.mortgage.model;
  requires transitive goedegep.finan.investmentinsurance.model;
  requires transitive goedegep.finan.jobappointment.model;
  requires goedegep.myworld.common;
  requires goedegep.model.configuration;
  requires goedegep.resources;
}