package goedegep.jfx.eobjecttable;

import org.eclipse.emf.ecore.EObject;

public interface EObjectEditor<T extends EObject> {
  
  public void setEObject(T eObject);
  
}
