module goedegep.events.model {
  exports goedegep.events.model;
  exports goedegep.events.model.util;
  exports goedegep.events.model.impl;

  requires transitive goedegep.types.model;
  requires goedegep.util;
  requires transitive org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
}