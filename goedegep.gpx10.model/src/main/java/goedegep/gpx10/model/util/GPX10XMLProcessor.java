/**
 */
package goedegep.gpx10.model.util;

import goedegep.gpx10.model.GPX10Package;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class GPX10XMLProcessor extends XMLProcessor {

  /**
   * Public constructor to instantiate the helper.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public GPX10XMLProcessor() {
    super((EPackage.Registry.INSTANCE));
    GPX10Package.eINSTANCE.eClass();
  }
  
  /**
   * Register for "*" and "xml" file extensions the GPX10ResourceFactoryImpl factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected Map<String, Resource.Factory> getRegistrations() {
    if (registrations == null) {
      super.getRegistrations();
      registrations.put(XML_EXTENSION, new GPX10ResourceFactoryImpl());
      registrations.put(STAR_EXTENSION, new GPX10ResourceFactoryImpl());
    }
    return registrations;
  }

} //GPX10XMLProcessor
