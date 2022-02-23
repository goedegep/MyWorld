/**
 */
package goedegep.gpx.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Person Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 	 A person or organization.
 *     
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.PersonType#getName <em>Name</em>}</li>
 *   <li>{@link goedegep.gpx.model.PersonType#getEmail <em>Email</em>}</li>
 *   <li>{@link goedegep.gpx.model.PersonType#getLink <em>Link</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx.model.GPXPackage#getPersonType()
 * @model extendedMetaData="name='personType' kind='elementOnly'"
 * @generated
 */
public interface PersonType extends EObject {
  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		Name of person or organization.
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see goedegep.gpx.model.GPXPackage#getPersonType_Name()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.PersonType#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Email</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		Email address.
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Email</em>' containment reference.
   * @see #setEmail(EmailType)
   * @see goedegep.gpx.model.GPXPackage#getPersonType_Email()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='email' namespace='##targetNamespace'"
   * @generated
   */
  EmailType getEmail();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.PersonType#getEmail <em>Email</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Email</em>' containment reference.
   * @see #getEmail()
   * @generated
   */
  void setEmail(EmailType value);

  /**
   * Returns the value of the '<em><b>Link</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		Link to Web site or other external information about person.
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Link</em>' containment reference.
   * @see #setLink(LinkType)
   * @see goedegep.gpx.model.GPXPackage#getPersonType_Link()
   * @model containment="true"
   *        extendedMetaData="kind='element' name='link' namespace='##targetNamespace'"
   * @generated
   */
  LinkType getLink();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.PersonType#getLink <em>Link</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Link</em>' containment reference.
   * @see #getLink()
   * @generated
   */
  void setLink(LinkType value);

} // PersonType
