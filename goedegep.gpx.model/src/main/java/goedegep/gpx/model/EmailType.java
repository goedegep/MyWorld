/**
 */
package goedegep.gpx.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Email Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 	 An email address.  Broken into two parts (id and domain) to help prevent email harvesting.
 *     
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.EmailType#getId <em>Id</em>}</li>
 *   <li>{@link goedegep.gpx.model.EmailType#getDomain <em>Domain</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx.model.GPXPackage#getEmailType()
 * @model extendedMetaData="name='emailType' kind='empty'"
 * @generated
 */
public interface EmailType extends EObject {
  /**
   * Returns the value of the '<em><b>Id</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		id half of email address (billgates2004)
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Id</em>' attribute.
   * @see #setId(String)
   * @see goedegep.gpx.model.GPXPackage#getEmailType_Id()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='id'"
   * @generated
   */
  String getId();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.EmailType#getId <em>Id</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Id</em>' attribute.
   * @see #getId()
   * @generated
   */
  void setId(String value);

  /**
   * Returns the value of the '<em><b>Domain</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		domain half of email address (hotmail.com)
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Domain</em>' attribute.
   * @see #setDomain(String)
   * @see goedegep.gpx.model.GPXPackage#getEmailType_Domain()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
   *        extendedMetaData="kind='attribute' name='domain'"
   * @generated
   */
  String getDomain();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.EmailType#getDomain <em>Domain</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Domain</em>' attribute.
   * @see #getDomain()
   * @generated
   */
  void setDomain(String value);

} // EmailType
