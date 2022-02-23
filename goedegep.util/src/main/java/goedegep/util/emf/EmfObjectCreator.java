package goedegep.util.emf;

import org.eclipse.emf.ecore.EObject;

@FunctionalInterface
public interface EmfObjectCreator<E extends EObject> {
  public E createEObject();
}
