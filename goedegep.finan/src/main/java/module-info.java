module goedegep.app.finan {
  exports goedegep.finan.investmentinsurances;
  exports goedegep.finan.jobappointment;
  exports goedegep.finan.guifx;
  exports goedegep.finan.jobappointment.guifx;
  exports goedegep.finan.pensioen.nn;
  exports goedegep.finan.mortgage;
  exports goedegep.finan.mortgage.guifx;
  exports goedegep.finan.investmentinsurances.guifx;
    
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