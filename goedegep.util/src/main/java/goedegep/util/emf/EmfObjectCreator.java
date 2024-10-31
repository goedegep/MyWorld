package goedegep.util.emf;

import org.eclipse.emf.ecore.EObject;

/**
 * This functional interface defines a method for creating an EObject (subtype).
 *
 * @param <E> The type of the object to be created.
 */
@FunctionalInterface
public interface EmfObjectCreator<E extends EObject> {
  E createEObject();
}
