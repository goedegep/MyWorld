module goedegep.model.rolodex {
  exports goedegep.rolodex.model;
  exports goedegep.rolodex.model.util;
  exports goedegep.rolodex.model.impl;

  opens goedegep.rolodex.model.impl;

  requires goedegep.types.model;
  requires java.logging;
  requires transitive org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
  requires transitive goedegep.util;
}