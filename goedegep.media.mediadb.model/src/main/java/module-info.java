module goedegep.media.mediadb.model {
  exports goedegep.media.mediadb.model;
  exports goedegep.media.mediadb.model.impl;
  exports goedegep.media.mediadb.model.util;

  requires goedegep.types.model;
  requires transitive goedegep.util;
  requires java.logging;
  requires org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
}