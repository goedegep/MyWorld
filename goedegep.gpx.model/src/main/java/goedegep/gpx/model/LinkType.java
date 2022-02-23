/**
 */
package goedegep.gpx.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 	 A link to an external resource (Web page, digital photo, video clip, etc) with additional information.
 *     
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link goedegep.gpx.model.LinkType#getText <em>Text</em>}</li>
 *   <li>{@link goedegep.gpx.model.LinkType#getType <em>Type</em>}</li>
 *   <li>{@link goedegep.gpx.model.LinkType#getHref <em>Href</em>}</li>
 * </ul>
 *
 * @see goedegep.gpx.model.GPXPackage#getLinkType()
 * @model extendedMetaData="name='linkType' kind='elementOnly'"
 * @generated
 */
public interface LinkType extends EObject {
  /**
   * Returns the value of the '<em><b>Text</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		Text of hyperlink.
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Text</em>' attribute.
   * @see #setText(String)
   * @see goedegep.gpx.model.GPXPackage#getLinkType_Text()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='text' namespace='##targetNamespace'"
   * @generated
   */
  String getText();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.LinkType#getText <em>Text</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Text</em>' attribute.
   * @see #getText()
   * @generated
   */
  void setText(String value);

  /**
   * Returns the value of the '<em><b>Type</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		Mime type of content (image/jpeg)
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Type</em>' attribute.
   * @see #setType(String)
   * @see goedegep.gpx.model.GPXPackage#getLinkType_Type()
   * @model dataType="org.eclipse.emf.ecore.xml.type.String"
   *        extendedMetaData="kind='element' name='type' namespace='##targetNamespace'"
   * @generated
   */
  String getType();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.LinkType#getType <em>Type</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' attribute.
   * @see #getType()
   * @generated
   */
  void setType(String value);

  /**
   * Returns the value of the '<em><b>Href</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * <!-- begin-model-doc -->
   * 
   * 		URL of hyperlink.
   * 	  
   * <!-- end-model-doc -->
   * @return the value of the '<em>Href</em>' attribute.
   * @see #setHref(String)
   * @see goedegep.gpx.model.GPXPackage#getLinkType_Href()
   * @model dataType="org.eclipse.emf.ecore.xml.type.AnyURI" required="true"
   *        extendedMetaData="kind='attribute' name='href'"
   * @generated
   */
  String getHref();

  /**
   * Sets the value of the '{@link goedegep.gpx.model.LinkType#getHref <em>Href</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Href</em>' attribute.
   * @see #getHref()
   * @generated
   */
  void setHref(String value);

} // LinkType
