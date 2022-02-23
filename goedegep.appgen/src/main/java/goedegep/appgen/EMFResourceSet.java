package goedegep.appgen;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

/**
 * 
 * This class provides an EMF ResourceSet singleton.
 * <p>
 * All EMF resources within an application can be related. So they should, or at least can, all be handled
 * by a single ResourceSet. This ResourceSet is provided by this class.<br/>
 * The default resource factory (EcoreResourceFactoryImpl()) is already registered for any extension ('*').<br/>
 * Any other required resource factory has to be added by a user of this resource set. 
 */
public class EMFResourceSet {
  private static ResourceSet resourceSet = null;
  
  private EMFResourceSet() {
  }

  /**
   * Get the ResourceSet singleton.<br/>
   * The default resource factory (EcoreResourceFactoryImpl()) is already registered for any extension ('*').
   * 
   * @return the ResourceSet singleton
   */
  public static ResourceSet getResourceSet() {
    if (resourceSet == null) {
      resourceSet = new ResourceSetImpl();
      
      resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
          Resource.Factory.Registry.DEFAULT_EXTENSION, new EcoreResourceFactoryImpl());
    }
    
    return resourceSet;
  }
}
