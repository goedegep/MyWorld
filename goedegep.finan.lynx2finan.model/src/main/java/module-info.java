module goedegep.finan.lynx2finan.model {
  exports goedegep.finan.lynx2finan.model.impl;
  exports goedegep.finan.lynx2finan.model;
  exports goedegep.finan.lynx2finan.model.util;

  requires goedegep.types.model;
  requires transitive goedegep.util;
  requires java.logging;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
}