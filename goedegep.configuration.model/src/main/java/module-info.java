module goedegep.model.configuration {
  exports goedegep.configuration.model;
  exports goedegep.configuration.model.util;

  requires goedegep.types.model;
  requires java.desktop;
  requires transitive org.eclipse.emf.common;
  requires org.eclipse.emf.ecore;
  requires transitive javafx.graphics;
}