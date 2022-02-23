module goedegep.finan.investmentinsurance.model {
  exports goedegep.finan.investmentinsurance.model;
  exports goedegep.finan.investmentinsurance.model.impl;
  exports goedegep.finan.investmentinsurance.model.util;

  requires transitive goedegep.model.rolodex;
  requires transitive goedegep.types.model;
  requires transitive goedegep.util;
  requires java.logging;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
}