package goedegep.jfx.eobjecttable;

import org.eclipse.emf.ecore.EObject;

public interface EObjectEditor<T extends EObject> {
  
  void setEObject(T eObject);
  
}
