module goedegep.finan.mortgage.model {
  exports goedegep.finan.mortgage.model;
  exports goedegep.finan.mortgage.model.util;

  requires transitive goedegep.model.rolodex;
  requires goedegep.types.model;
  requires transitive goedegep.util;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
}