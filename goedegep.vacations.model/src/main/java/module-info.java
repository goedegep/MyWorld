module goedegep.model.vacations {
  exports goedegep.vacations.model;
  exports goedegep.vacations.model.util;

  requires transitive goedegep.types.model;
  requires transitive goedegep.poi.model;
  requires goedegep.util;
  requires java.logging;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
  requires org.eclipse.emf.ecore.xmi;
}